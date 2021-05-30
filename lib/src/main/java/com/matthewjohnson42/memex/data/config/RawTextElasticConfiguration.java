package com.matthewjohnson42.memex.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("enableelasticrepositories")
@Configuration
public class RawTextElasticConfiguration extends AbstractElasticConfiguration {

    public RawTextElasticConfiguration() {
        this.createIndexResourceFile = "elasticsearchqueries/rawTextCreateIndex.json";
    }

    public String getRawTextSearchById() {
        return readResource("elasticsearchqueries/rawTextSearchById.json");
    }

    public String getRawTextSearchByTextContentFuzzy() {
        return readResource("elasticsearchqueries/rawTextSearchByTextContentFuzzy.json");
    }

}
