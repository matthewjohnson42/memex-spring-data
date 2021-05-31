package com.matthewjohnson42.memex.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Entities associated with the {@link com.matthewjohnson42.memex.data.repository.Repository} object
 *
 * @see com.matthewjohnson42.memex.data.repository.Repository
 * @see com.matthewjohnson42.memex.data.dto.DtoForEntity
 * @see com.matthewjohnson42.memex.data.service.DataService
 * @see com.matthewjohnson42.memex.data.converter.DtoEntityConverter
 */
public abstract class Entity<ID> {

    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public abstract ID getId();

    public abstract Entity<ID> setId(ID id);

    public Entity() { }

    public Entity(Entity<ID> entity) {
        this.createDateTime = entity.getCreateDateTime();
        this.updateDateTime = entity.getUpdateDateTime();
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public Entity setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Entity setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

}
