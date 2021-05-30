package com.matthewjohnson42.memex.data.entity.elasticsearch;

import com.matthewjohnson42.memex.data.entity.Entity;

/**
 * Class used to represent the raw text entity as an ElasticSearch entity.
 * Raw text entity is only text and DB tracking data.
 */
public class RawTextES extends Entity<String> {

    private String id;
    private String textContent;

    public RawTextES() { }

    public RawTextES (RawTextES rawTextES) {
        super(rawTextES);
        this.id = rawTextES.getId();
        this.textContent = rawTextES.getTextContent();
    }

    @Override
    public String getId() {
        return id;
    }

    public RawTextES setId(String id) {
        this.id = id;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public RawTextES setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

}
