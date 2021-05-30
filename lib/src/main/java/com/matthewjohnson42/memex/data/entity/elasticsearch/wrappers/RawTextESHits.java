package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import java.util.List;

public class RawTextESHits {

    private RawTextESTotal total;
    private List<RawTextESHit> hits;

    public RawTextESHits() { }

    public void setTotal(RawTextESTotal total) {
        this.total = total;
    }
    public RawTextESTotal getTotal() {
        return total;
    }

    public void setHits(List<RawTextESHit> hits) {
        this.hits = hits;
    }
    public List<RawTextESHit> getHits() {
        return hits;
    }

}
