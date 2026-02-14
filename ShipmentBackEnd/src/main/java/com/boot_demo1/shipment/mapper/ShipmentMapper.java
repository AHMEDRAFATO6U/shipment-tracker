package com.boot_demo1.shipment.mapper;

import com.boot_demo1.shipment.dto.ShipmentResponse;
import com.boot_demo1.shipment.model.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    ShipmentMapper SHIPMENT_MAPPER = Mappers.getMapper(ShipmentMapper.class);

    ShipmentResponse toResponse(Shipment shipment);


}
