package com.sia.taco.cloud.server.mapper;

import com.sia.taco.cloud.api.entity.TacoOrder;
import com.sia.taco.cloud.api.dto.TacoOrderDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TacoOrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TacoOrder mapToTacoOrderPatched(@MappingTarget TacoOrder target, TacoOrderDto source);
}
