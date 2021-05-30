package com.matthewjohnson42.memex.data.converter;

import com.matthewjohnson42.memex.data.dto.DtoForEntity;
import com.matthewjohnson42.memex.data.entity.Entity;

/**
 * A converter for controller/service data transfer objects and repository entities. Enforces data restrictions.
 * @param <ID> the type of the id of the dto and entity
 * @param <D> the type of the dto
 * @param <E> the type of the entity
 *
 * @see Entity
 * @see DtoForEntity
 * @see com.matthewjohnson42.memex.data.repository.Repository
 * @see com.matthewjohnson42.memex.data.service.DataService
 */
public interface DtoEntityConverter<ID, D extends DtoForEntity<ID>, E extends Entity<ID>> {

    public E convertDto(D d);

    public D convertEntity(E e);

    default public E updateFromDto(E e, D d) {
        if (d.getId() != null) {
            e.setId(d.getId());
        }
        return e;
    };

    default public D updateFromEntity(D d, E e) {
        if (e.getId() != null) {
            d.setId(e.getId());
        }
        if (e.getCreateDateTime() != null) {
            d.setCreateDateTime(e.getCreateDateTime());
        }
        if (e.getUpdateDateTime() != null) {
            d.setUpdateDateTime(e.getUpdateDateTime());
        }
        return d;
    };

}
