package com.matthewjohnson42.memex.data.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO representing the raw text entity as handled by the controller.
 * Raw text entity is only text and DB tracking information
 */
public class RawTextDto extends DtoForEntity<String> {

    @JsonIgnore // along with getter and setter annotations, do not deserialize value
    private String id;
    private String textContent;

    public RawTextDto() { }

    public RawTextDto(RawTextDto rawTextDto) {
        super(rawTextDto);
        this.id = rawTextDto.getId();
        this.textContent = rawTextDto.getTextContent();
    }

    @JsonCreator
    public RawTextDto(@JsonProperty(value = "textContent", required = true) String textContent) {
        this.textContent = textContent;
    }

    @Override
    @JsonIgnore
    public RawTextDto setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    @JsonProperty
    public String getId() {
        return id;
    }

    public RawTextDto setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

}
