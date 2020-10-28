package ru.vtb.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface AbstractMapper<E, D> {
    D toDto(E entity);
    E toEntity(D entity);

    default List<D> toDtos(List<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    default Optional<D> toOptionalDto(Optional<E> entity) {
        return entity.map(this::toDto);
    }
}
