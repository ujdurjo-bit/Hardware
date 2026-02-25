package hr.java.web.Hardware.service;

import hr.java.web.Hardware.dto.HardwareDTO;

import java.util.List;
import java.util.Optional;

public interface HardwareService {
    List<HardwareDTO> getAllHardware();
    List<HardwareDTO> getHardwareBySNum(String Snum);
    Integer saveNewHardware(HardwareDTO article);
    Optional<HardwareDTO> updateHardware(HardwareDTO articleDTO, Integer id);
    boolean hardwareByIdExists(Integer id);
    boolean deleteHardwareById(Integer id);
}