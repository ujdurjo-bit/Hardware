package hr.java.web.Hardware.service;


import hr.java.web.Hardware.domain.Hardware;
import hr.java.web.Hardware.domain.HardwareType;
import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.repository.HardwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private Hardware convertHardwareDtoToHardware(HardwareDTO hardwareDTO) {
        Integer latestId =
                hardwareRepository.getAllHardware().stream()
                        .max((a1, a2) -> a1.getId().compareTo(a2.getId()))
                        .get().getId();

        return new Hardware(latestId + 1, hardwareDTO.getHardwareName(),
                hardwareDTO.getHardwareSerialNumber(), hardwareDTO.getHardwarePrice(), HardwareType.valueOf(hardwareDTO.getHardwareType()), hardwareDTO.getHardwareQuantity());
    }


    @Override
    public Integer saveNewHardware(HardwareDTO hardware) {
        return hardwareRepository.saveNewHardware(convertHardwareDtoToHardware(hardware));
    }

    @Override
    public Optional<HardwareDTO> updateHardware(HardwareDTO hardwareDTO, Integer id) {
        Optional<Hardware> updatedHardwareOptional =
                hardwareRepository.updateHardware(convertHardwareDtoToHardware(hardwareDTO), id);

        if (updatedHardwareOptional.isPresent()) {
            return Optional.of(convertHardwareToHardwareDTO(updatedHardwareOptional.get()));
        }

        return Optional.empty();
    }

    @Override
    public boolean hardwareByIdExists(Integer id) {
        return hardwareRepository.hardwareByIdExists(id);
    }

    @Override
    public boolean deleteHardwareById(Integer id) {
        return hardwareRepository.deleteHardwareById(id);
    }


}

