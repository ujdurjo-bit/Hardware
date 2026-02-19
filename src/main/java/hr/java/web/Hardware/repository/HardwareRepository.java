package hr.java.web.Hardware.repository;

import hr.java.web.Hardware.domain.Hardware;

import java.util.List;

public interface HardwareRepository {
    List<Hardware> getAllHardware();
    List<Hardware> getHardwareBySNum(String Snum);
    void saveNewHardware(Hardware hardware);


}
