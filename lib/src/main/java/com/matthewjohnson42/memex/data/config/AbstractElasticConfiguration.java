package com.matthewjohnson42.memex.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Profile("enableelasticrepositories")
@Configuration
public abstract class AbstractElasticConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${db.elasticsearch.hostname}")
    private String hostName;

    @Value("${db.elasticsearch.port}")
    private String hostPort;

    protected String createIndexResourceFile;

    public final String getHostName() {
        return hostName;
    }

    public final String getHostPort() {
        return hostPort;
    }

    public String getCreateIndex() {
        return readResource(createIndexResourceFile);
    }

    protected String readResource(String resource) {
        StringBuilder sb = new StringBuilder();
        ClassPathResource classPathResource = new ClassPathResource(resource);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
            reader.lines().forEach(sb::append);
        } catch (IOException e) {
            logger.error("Exception when reading from resource.", e);
        }
        return sb.toString();
    }

}
