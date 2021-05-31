package com.matthewjohnson42.memex.data.converter;

/**
 * A converter for distinct DTO types. Enforces data restrictions.
 *
 * @param <D1> dto type 1
 * @param <E2> dto type 2
 * @see DtoEntityConverter
 */
public interface DtoConverter<D1, D2> {

    public D2 convertDtoType1(D1 d);

    public D1 convertDtoType2(D2 d);

}
