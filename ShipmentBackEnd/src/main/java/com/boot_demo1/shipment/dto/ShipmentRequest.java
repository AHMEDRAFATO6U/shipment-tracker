package com.boot_demo1.shipment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequest {

    @NotBlank(message = "Origin is required")
    public String origin;

    @NotBlank(message = "Destination is required")
    public String destination;

    public String estimatedDelivery;
}
