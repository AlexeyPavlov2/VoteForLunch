package org.javatraining.voteforlunch.util.entity;

import java.util.List;
import java.util.stream.Collectors;

public interface EntityUtil<ObjectEntity, ObjectDto> {
    ObjectDto createDtoFromEntity(ObjectEntity objectEntity);

    default List<ObjectDto> createDtoListFromEntityList(List<ObjectEntity> objectEntityList) {
        return objectEntityList.stream().map(this::createDtoFromEntity).collect(Collectors.toList());
    };

    ObjectEntity createEntityFromDto(ObjectDto objectDto);

    ObjectEntity updateEntityFromDto(ObjectEntity objectEntity, ObjectDto objectDto);

    ObjectEntity createNewEntityFromAnother(ObjectEntity objectEntity);

}
