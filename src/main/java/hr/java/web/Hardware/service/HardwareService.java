package hr.java.web.Hardware.service;

import hr.java.web.Hardware.dto.HardwareDTO;

import java.util.List;

public interface HardwareService {
    List<HardwareDTO> getAllHardware();
    List<HardwareDTO> getHardwareBySNum(String Snum);
}