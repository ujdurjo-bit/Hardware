package hr.java.web.Hardware.service;


import hr.java.web.Hardware.domain.Hardware;
import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.repository.HardwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    private HardwareRepository hardwareRepository;

    @Override
    public List<HardwareDTO> getAllHardware() {
        return hardwareRepository.getAllHardware().stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();
    }

    @Override
    public List<HardwareDTO> getHardwareBySNum(String Snum) {
        return hardwareRepository.getHardwareBySNum(Snum).stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();
    }

    private HardwareDTO convertHardwareToHardwareDTO(Hardware hardware) {
        return new HardwareDTO(hardware.getName(),
                hardware.getSNum(), hardware.getPrice(),
                hardware.getType().name(), hardware.getQuantity());
    }


}
