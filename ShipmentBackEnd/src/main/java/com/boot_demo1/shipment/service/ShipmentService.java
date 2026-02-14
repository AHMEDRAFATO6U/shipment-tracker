package com.boot_demo1.shipment.service;

import com.boot_demo1.shipment.dto.ShipmentRequest;
import com.boot_demo1.shipment.dto.ShipmentResponse;
import com.boot_demo1.shipment.dto.UpdateStatusRequest;
import com.boot_demo1.shipment.model.Shipment;
import jakarta.validation.Valid;

import java.util.List;

public interface ShipmentService {
    ShipmentResponse createShipment(@Valid ShipmentRequest shipmentRequest);

    

    ShipmentResponse getShipmentById(Long id);

    List<ShipmentResponse> getAllShipments();

    ShipmentResponse getBytrackingNumber(@Valid String trackingNumber);

    ShipmentResponse updateShipmentStatus(@Valid UpdateStatusRequest request, Long id);
}
