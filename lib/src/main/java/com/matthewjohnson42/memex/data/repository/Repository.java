package com.matthewjohnson42.memex.data.repository;

import com.matthewjohnson42.memex.data.entity.Entity;

import java.util.Optional;

/**
 * Implementation of a generic Repository, spanning Mongo and ES data stores
 *
 * @param <T>  the entity type being stored
 * @param <ID> the id type of the entity type specified by <T>
 * @see com.matthewjohnson42.memex.data.entity.Entity
 * @see com.matthewjohnson42.memex.data.dto.DtoForEntity
 * @see com.matthewjohnson42.memex.data.converter.DtoEntityConverter
 * @see com.matthewjohnson42.memex.data.service.DataService
 */
public interface Repository<T extends Entity, ID> {

    public T save(T e);

    public Optional<T> findById(ID id);

    public void deleteById(ID id);

}
