package com.boot_demo1.shipment.service.impe;

import com.boot_demo1.shipment.dto.ShipmentRequest;
import com.boot_demo1.shipment.dto.ShipmentResponse;
import com.boot_demo1.shipment.dto.StatusUpdateMessage;
import com.boot_demo1.shipment.dto.UpdateStatusRequest;
import com.boot_demo1.shipment.enums.ShipmentStatus;
import com.boot_demo1.shipment.exception.ShipmentNotFoundException;
import com.boot_demo1.shipment.mapper.ShipmentMapper;
import com.boot_demo1.shipment.model.Shipment;
import com.boot_demo1.shipment.repository.ShipmentRepository;
import com.boot_demo1.shipment.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShipmentServiceImple implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
   private ShipmentMapper shipmentMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public ShipmentResponse  createShipment(ShipmentRequest shipmentRequest) {

        String trackNumber = generateTrackingNumber();

        Shipment shipment =  Shipment.builder()
                .trackingNumber(trackNumber)
                .origin(shipmentRequest.origin)
                .destination(shipmentRequest.destination)
                .estimatedDelivery(shipmentRequest.estimatedDelivery)
                .build();
        shipmentRepository.save(shipment);
        notifyShipmentStatus(shipment, getStatusMessage(shipment.getStatus()));
        return shipmentMapper.toResponse(shipment);

    }

    @Override
    public List<ShipmentResponse> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();
        if (shipments.isEmpty()) {
            throw new ShipmentNotFoundException("No shipments found");
        }
        return shipments.stream()
                .map(shipmentMapper::toResponse)
                .collect(Collectors.toList());
    }



    @Override
    public ShipmentResponse getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with id: " + id ));
        return shipmentMapper.toResponse(shipment);
    }


    @Override
    public ShipmentResponse getBytrackingNumber(String trackingNumber) {
       Shipment shipment =shipmentRepository.findByTrackingNumber(trackingNumber)
               .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with trackingNumber: " + trackingNumber ));
       return shipmentMapper.toResponse(shipment);
    }

    @Override
    public ShipmentResponse updateShipmentStatus(UpdateStatusRequest request, Long id) {

        Shipment shipment =shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with id: " + id ));

        shipment.setStatus(shipment.getStatus());
        if(request.getCurrentLocation() != null) {
            shipment.setCurrentLocation(request.getCurrentLocation());
        }
        shipmentRepository.save(shipment);
        notifyShipmentStatus(shipment, getStatusMessage(shipment.getStatus()));
        return shipmentMapper.toResponse(shipment);

    }

    public void notifyShipmentStatus(Shipment shipment, String message) {
        var update = StatusUpdateMessage.builder()
                .shipmentId(shipment.getId())
                .trackingNumber(shipment.getTrackingNumber())
                .status(shipment.getStatus())
                .currentLocation(shipment.getCurrentLocation())
                .timestamp(shipment.getUpdatedAt())
                .message(message)
                .build();

        messagingTemplate.convertAndSend("/topic/shipments", update);
        messagingTemplate.convertAndSend("/topic/shipments" + shipment.getId(), update);

        log.info("Sent shipment status update: {}", update);
    }

    private String getStatusMessage(ShipmentStatus status) {
        return switch (status) {
            case ORDER_PLACED -> "Order has been placed";
            case PROCESSING -> "Order is being processed";
            case PICKED_UP -> "Package has been picked up";
            case IN_TRANSIT -> "Package is in transit";
            case OUT_FOR_DELIVERY -> "Package is out for delivery";
            case DELIVERED -> "Package has been delivered";
            case EXCEPTION -> "Delivery exception occurred";
        };
    }


    private String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
