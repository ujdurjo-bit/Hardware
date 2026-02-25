package hr.java.web.Hardware.repository;

import hr.java.web.Hardware.domain.Hardware;

import java.util.List;
import java.util.Optional;

public interface HardwareRepository {
    List<Hardware> getAllHardware();
    List<Hardware> getHardwareBySNum(String Snum);
    Integer saveNewHardware(Hardware hardware);
    Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Integer id);
    boolean hardwareByIdExists(Integer id);
    boolean deleteHardwareById(Integer id);

}
