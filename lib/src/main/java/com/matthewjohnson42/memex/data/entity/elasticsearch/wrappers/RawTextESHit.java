package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import com.matthewjohnson42.memex.data.entity.elasticsearch.RawTextES;

public class RawTextESHit {

    private RawTextES _source;
    private RawTextESHighlight highlight;

    public RawTextESHit() { }

    public void set_source(RawTextES _source) {
        this._source = _source;
    }
    public RawTextES get_source() {
        return _source;
    }

    public RawTextESHit setHighlight(RawTextESHighlight highlight) {
        this.highlight = highlight;
        return this;
    }
    public RawTextESHighlight getHighlight() {
        return highlight;
    }

}
