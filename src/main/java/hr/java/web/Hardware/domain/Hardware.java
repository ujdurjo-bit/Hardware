package hr.java.web.Hardware.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hardware {
    private Integer id;
    private String name;
    private String SNum;
    private BigDecimal price;
    private HardwareType type;
    private Integer quantity;
}
