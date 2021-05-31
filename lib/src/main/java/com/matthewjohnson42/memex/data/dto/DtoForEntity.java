package com.matthewjohnson42.memex.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Describes DTOs associated with an {@link com.matthewjohnson42.memex.data.entity.Entity}
 * Returned from {@link com.matthewjohnson42.memex.data.service.DataService} extensions
 *
 * @param <ID> the class of the id
 * @see com.matthewjohnson42.memex.data.entity.Entity
 * @see com.matthewjohnson42.memex.data.service.DataService
 * @see com.matthewjohnson42.memex.data.repository.Repository
 * @see com.matthewjohnson42.memex.data.converter.DtoEntityConverter
 */
public abstract class DtoForEntity<ID> {

    @JsonIgnore
    private LocalDateTime createDateTime;
    @JsonIgnore
    private LocalDateTime updateDateTime;

    public DtoForEntity() { }

    public DtoForEntity(DtoForEntity<ID> dto) {
        this.createDateTime = dto.getCreateDateTime();
        this.updateDateTime = dto.getUpdateDateTime();
    }

    public abstract ID getId();

    public abstract DtoForEntity<ID> setId(ID id);

    @JsonIgnore
    public DtoForEntity setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    @JsonIgnore
    public DtoForEntity setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

}
