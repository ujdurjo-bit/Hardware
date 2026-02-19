package hr.java.web.Hardware.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareDTO {
    private String hardwareName;
    private String hardwareSerialNumber;
    private BigDecimal hardwarePrice;
    private String hardwareType;
    private Integer hardwareQuantity;
}
