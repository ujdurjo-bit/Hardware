package hr.java.web.Hardware.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareDTO {
    @NotBlank(message = "Hardware name cannot be blank")
    private String hardwareName;

    @NotBlank(message = "Hardware serial number cannot be blank")
    private String hardwareSerialNumber;

    @DecimalMin(value = "0.0", message = "Hardware price must be positive")
    private BigDecimal hardwarePrice;

    @NotBlank(message = "Hardware type cannot be blank")
    private String hardwareType;

    @NotNull(message = "Quantity must not be null")
    private Integer hardwareQuantity;
}
