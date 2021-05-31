package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

public class RawTextESWrapper {

    private RawTextESHits hits;

    public RawTextESWrapper() { }

    public void setHits(RawTextESHits hits) {
        this.hits = hits;
    }

    public RawTextESHits getHits() {
        return hits;
    }

}
