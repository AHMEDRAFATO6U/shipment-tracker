package com.boot_demo1.shipment.dto;

import com.boot_demo1.shipment.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusUpdateMessage {

    private Long shipmentId;
    private String trackingNumber;
    private ShipmentStatus status;
    private String currentLocation;
    private LocalDateTime timestamp;
    private String message;
}
