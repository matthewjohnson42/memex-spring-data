package com.matthewjohnson42.memex.data.repository.elasticsearch;

import com.matthewjohnson42.memex.data.config.RawTextElasticConfiguration;
import com.matthewjohnson42.memex.data.entity.elasticsearch.RawTextES;
import com.matthewjohnson42.memex.data.entity.elasticsearch.RawTextESComposite;
import com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers.RawTextESHit;
import com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers.RawTextESWrapper;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Custom implementation of an ElasticSearch REST client. Used for accessing the ElasticSearch data store.
 * Chosen to address the failure of ElasticSearch to recognize complex, base 64 encoded queries submitted by
 * spring-data-elasticsearch.
 *
 * @see com.matthewjohnson42.memex.data.repository.Repository
 */
@Profile("enableelasticrepositories")
@Component
public class RawTextESRestTemplate extends ElasticRestTemplate<String, RawTextES> {

    private final String rawTextSearchByIdQuery;
    private final String rawTextSearchByTextContentFuzzyQuery;

    private final Integer fuzziness = 1;

    public RawTextESRestTemplate(RawTextElasticConfiguration config) {
        super(config);
        this.rawTextSearchByIdQuery = config.getRawTextSearchById();
        this.rawTextSearchByTextContentFuzzyQuery = config.getRawTextSearchByTextContentFuzzy();
    }

    public Optional<RawTextES> findById(String id) {
        String query = String.format(rawTextSearchByIdQuery, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestBody = new HttpEntity<>(query, headers);
        RawTextESWrapper responseBody = postForObject(entitySearchUrl, requestBody, RawTextESWrapper.class);
        if (responseBody.getHits().getHits().size() > 0) {
            return Optional.of(responseBody.getHits().getHits().get(0).get_source());
        } else {
            return Optional.empty();
        }
    }

    public void deleteById(String id) {
        delete(entityDocUrl, id);
    }

    public RawTextES save(RawTextES rawTextES) {
        String rawTextId = rawTextES.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RawTextES> requestBody = new HttpEntity(rawTextES, headers);
        put(entityDocUrl, requestBody, rawTextId);
        return rawTextES; // do not use "findIfExists" to validate stored value given HTTP request latency and non-blocking nature
    }

    public Page<RawTextESComposite> getPageFromSearchString(
            String searchString,
            LocalDateTime startCreateDate,
            LocalDateTime endCreateDate,
            LocalDateTime startUpdateDate,
            LocalDateTime endUpdateDate,
            Pageable pageable) {
        String query = getSearchQuery(searchString, startCreateDate, endCreateDate, startUpdateDate, endUpdateDate, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(query, headers);
        RawTextESWrapper responseBody = postForObject(entitySearchUrl, request, RawTextESWrapper.class);
        Integer totalHits = responseBody.getHits().getTotal().getValue();
        List<RawTextESComposite> pageContent = new ArrayList<>();
        for (RawTextESHit hit : responseBody.getHits().getHits()) {
            RawTextESComposite rawTextESComposite = new RawTextESComposite(hit.get_source());
            rawTextESComposite.setHighlights(hit.getHighlight().getTextContent());
            pageContent.add(rawTextESComposite);
        }
        return new PageImpl<>(pageContent, pageable, totalHits);
    }

    private String getSearchQuery(String searchString,
                                  LocalDateTime startCreateDate,
                                  LocalDateTime endCreateDate,
                                  LocalDateTime startUpdateDate,
                                  LocalDateTime endUpdateDate,
                                  Pageable pageable) {
        Assert.hasLength(searchString, "Search string cannot be null or the empty string");
        Assert.notNull(pageable, "Pageable cannot be null");
        Integer startIndex = pageable.getPageNumber() * pageable.getPageSize();
        Integer pageSize = pageable.getPageSize();
        String startCreate = startCreateDate == null ? "null" : "\"" + dateTimeFormatter.format(startCreateDate) + "\"";
        String endCreate = endCreateDate == null ? "null" : "\"" + dateTimeFormatter.format(endCreateDate) + "\"";
        String startUpdate = startUpdateDate == null ? "null" : "\"" + dateTimeFormatter.format(startUpdateDate) + "\"";
        String endUpdate = endUpdateDate == null ? "null" : "\"" + dateTimeFormatter.format(endUpdateDate) + "\"";
        return String.format(rawTextSearchByTextContentFuzzyQuery,
                startIndex, pageSize, searchString, fuzziness, startCreate, endCreate, startUpdate, endUpdate);
    }

}
