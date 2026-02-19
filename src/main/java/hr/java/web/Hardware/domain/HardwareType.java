package hr.java.web.Hardware.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HardwareType {
    CPU(1, "Processor", "Central Processing Unit"),
    GPU(2, "Graphics Card", "Graphics Processing Unit"),
    MBO(3, "Motherboard", "Mainboard"),
    RAM(4, "RAM", "Random Access Memory"),
    STORAGE(5, "Storage", "SSD or HDD"),
    OTHER(6, "Other", "Other hardware components");

    private final Integer id;
    private final String name;
    private final String description;
}