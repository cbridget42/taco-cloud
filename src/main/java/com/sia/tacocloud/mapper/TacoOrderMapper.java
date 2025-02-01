package com.sia.tacocloud.mapper;

import com.sia.tacocloud.dto.TacoOrderDto;
import com.sia.tacocloud.entity.TacoOrder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TacoOrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TacoOrder mapToTacoOrderPatched(@MappingTarget TacoOrder target, TacoOrderDto source);
}
