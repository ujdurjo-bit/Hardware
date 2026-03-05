package hr.java.web.Hardware.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "hardware")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hardware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String SNum;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private HardwareType type;
    private Integer quantity;
}
