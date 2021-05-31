package com.matthewjohnson42.memex.data.repository.elasticsearch;

import com.matthewjohnson42.memex.data.config.AbstractElasticConfiguration;
import com.matthewjohnson42.memex.data.entity.Entity;
import com.matthewjohnson42.memex.data.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * A rest template for accessing the ElasticSearch index of the generic type
 * @param <E> the type corresponding to the ES index
 *
 * @see com.matthewjohnson42.memex.data.repository.Repository
 */
public abstract class ElasticRestTemplate<ID, E extends Entity<ID>> extends RestTemplate implements Repository<E , ID> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String format = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    protected DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern(format).toFormatter();

    protected final String createIndexCommand;
    protected final String entityName;
    protected final String entityUrl;
    protected final String entityDocUrl;
    protected final String entitySearchUrl;

    public ElasticRestTemplate(AbstractElasticConfiguration config) {
        assert this.getClass().getSimpleName().endsWith("ESRestTemplate") : "Ancestors of ElasticRestTemplate must have class name suffix of 'ESRestTemplate' and a prefix of the entity type";
        entityName = this.getClass().getSimpleName().replace("ESRestTemplate", "").toLowerCase();
        createIndexCommand = config.getCreateIndex();
        entityUrl = String.format("http://%s:%s/%s", config.getHostName(), config.getHostPort(), entityName);
        entityDocUrl = String.format("http://%s:%s/%s/_doc/{id}", config.getHostName(), config.getHostPort(), entityName);
        entitySearchUrl = String.format("http://%s:%s/%s/_search", config.getHostName(), config.getHostPort(), entityName);
        initIndex();
    }

    protected void initIndex() {
        logger.info("Checking for existing ElasticSearch index '{}'", entityName);
        boolean indexExists = false;
        try {
            ResponseEntity response = getForEntity(entityUrl, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                indexExists = true;
                logger.info("Found existing ElasticSearch index '{}', will not attempt re-creation of index.", entityName);
            }
        } catch (Exception e) {
            if (!(e instanceof HttpClientErrorException.NotFound)) {
                logger.error("Error when checking for ElasticSearch index '{}'", entityName, e);
            }
        }
        if (!indexExists) {
            logger.info("Did not find existing ElasticSearch index '{}', proceeding with creation", entityName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(createIndexCommand, headers);
            put(entityUrl, request);
            logger.info("Created ElasticSearch index '{}'", entityName);
        }
    };

}
