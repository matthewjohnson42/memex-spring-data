package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import java.util.List;

public class RawTextESHighlight {

    private List<String> textContent;

    public RawTextESHighlight() { }

    public List<String> getTextContent() {
        return textContent;
    }
    public RawTextESHighlight setTextContent(List<String> textContent) {
        this.textContent = textContent;
        return this;
    }

}
