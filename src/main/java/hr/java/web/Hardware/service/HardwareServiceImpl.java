package hr.java.web.Hardware.service;


import hr.java.web.Hardware.domain.Hardware;
import hr.java.web.Hardware.domain.HardwareType;
import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.repository.SpringDataCategoryRepository;
import hr.java.web.Hardware.repository.SpringDataJpaHardwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HardwareServiceImpl implements HardwareService {

//    private HardwareRepository hardwareRepository;

    private SpringDataJpaHardwareRepository hardwareRepository;
    private SpringDataCategoryRepository hardwareTypeRepository;

    @Override
    public List<HardwareDTO> getAllHardware() {
        return hardwareRepository.findAll().stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();

/*        return hardwareRepository.getAllHardware().stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();*/
    }

    @Override
    public List<HardwareDTO> getHardwareBySNum(String Snum) {
        return hardwareRepository.findBySNum(Snum).stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();


 /*       return hardwareRepository.getHardwareBySNum(Snum).stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();*/
    }

    private HardwareDTO convertHardwareToHardwareDTO(Hardware hardware) {
        return new HardwareDTO(hardware.getName(),
                hardware.getSNum(), hardware.getPrice(),
                hardware.getType().getName(), hardware.getQuantity());


       /* return new HardwareDTO(hardware.getName(),
                hardware.getSNum(), hardware.getPrice(),
                hardware.getType().name(), hardware.getQuantity());*/
    }

    private Hardware convertHardwareDtoToHardware(HardwareDTO hardwareDTO) {

        Hardware hardware = new Hardware();
        hardware.setName(hardwareDTO.getHardwareName());
        hardware.setSNum(hardwareDTO.getHardwareSerialNumber());
        hardware.setPrice(hardwareDTO.getHardwarePrice());
        HardwareType type = hardwareTypeRepository.findByName(hardwareDTO.getHardwareType());
        if (type == null) {
            throw new IllegalArgumentException("Type with name '" + hardwareDTO.getHardwareType() + "' not found.");

        }
        hardware.setType(type);
        return hardware;



/*        return new Hardware(
                null,
                hardwareDTO.getHardwareName(),
                hardwareDTO.getHardwareSerialNumber(),
                hardwareDTO.getHardwarePrice(),
                HardwareType.valueOf(hardwareDTO.getHardwareType()),
                hardwareDTO.getHardwareQuantity()
        );*/
    }


    @Override
    public HardwareDTO saveNewHardware(HardwareDTO hardware) {
        return convertHardwareToHardwareDTO(hardwareRepository.save(convertHardwareDtoToHardware(hardware)));

       // return hardwareRepository.saveNewHardware(convertHardwareDtoToHardware(hardware));
    }

    @Override
    public Optional<HardwareDTO> updateHardware(HardwareDTO hardwareDTO, Integer id) {
        Optional<Hardware> hardwareToUpdate = hardwareRepository.findById(id.longValue());

        if (hardwareToUpdate.isPresent()) {
            Hardware hardware = hardwareToUpdate.get();
            hardware.setType(hardwareTypeRepository.findByName(hardwareDTO.getHardwareType()));
            hardware.setPrice(hardwareDTO.getHardwarePrice());
            hardware.setSNum(hardwareDTO.getHardwareSerialNumber());
            hardware.setName(hardwareDTO.getHardwareName());
            Hardware updatedHardware = hardwareRepository.save(hardware);
            return Optional.of(convertHardwareToHardwareDTO(updatedHardware));
        }

        return Optional.empty();




    /*    Optional<Hardware> updatedHardwareOptional =
                hardwareRepository.updateHardware(convertHardwareDtoToHardware(hardwareDTO), id);

        if (updatedHardwareOptional.isPresent()) {
            return Optional.of(convertHardwareToHardwareDTO(updatedHardwareOptional.get()));
        }

        return Optional.empty();*/
    }

    @Override
    //public boolean hardwareByIdExists(Integer id) {return hardwareRepository.hardwareByIdExists(id);
    public boolean hardwareByIdExists(Integer id) {return hardwareRepository.findById(id.longValue()).isPresent();




   //public boolean deleteHardwareById(Integer id) {return hardwareRepository.deleteHardwareById(id);
    }
    @Override
    public boolean deleteHardwareById(Integer id) {
        if (hardwareByIdExists(id)) {
            hardwareRepository.deleteById(id.longValue());
            return true;
        } else {
            return false;
        }
    }

}

