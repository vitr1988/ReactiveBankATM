package ru.vtb.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface AbstractMapper<E, D> {
    D toDto(E document);
    E toDocument(D dto);

    default List<D> toDtos(List<E> documents) {
        return documents.stream().map(this::toDto).collect(Collectors.toList());
    }

    default Optional<D> toOptionalDto(Optional<E> document) {
        return document.map(this::toDto);
    }
}
