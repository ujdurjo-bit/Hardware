package hr.java.web.Hardware.repository;


import hr.java.web.Hardware.domain.Hardware;
import hr.java.web.Hardware.domain.HardwareType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MockHardwareRepository implements HardwareRepository {

    private static List<Hardware> hardwareList;

    static {
        hardwareList = new ArrayList<>();

        Hardware cpu1 = new Hardware(1, "Intel Core i7-13700K", "CPU-001",
                new BigDecimal("450.00"), HardwareType.CPU, 15);
        Hardware cpu2 = new Hardware(2, "AMD Ryzen 9 7950X", "CPU-002",
                new BigDecimal("700.00"), HardwareType.CPU, 8);
        Hardware gpu1 = new Hardware(3, "NVIDIA RTX 4080", "GPU-001",
                new BigDecimal("1200.00"), HardwareType.GPU, 5);
        Hardware gpu2 = new Hardware(4, "AMD Radeon RX 7900 XT", "GPU-002",
                new BigDecimal("900.00"), HardwareType.GPU, 7);
        Hardware mbo1 = new Hardware(5, "ASUS ROG STRIX Z790-E", "MBO-001",
                new BigDecimal("400.00"), HardwareType.MBO, 10);
        Hardware ram1 = new Hardware(6, "Corsair Vengeance 32GB DDR5", "RAM-001",
                new BigDecimal("180.00"), HardwareType.RAM, 25);
        Hardware storage1 = new Hardware(7, "Samsung 980 Pro 1TB NVMe", "STORAGE-001",
                new BigDecimal("120.00"), HardwareType.STORAGE, 30);
        Hardware other1 = new Hardware(8, "Noctua NH-D15 CPU Cooler", "OTHER-001",
                new BigDecimal("100.00"), HardwareType.OTHER, 12);

        hardwareList.add(cpu1);
        hardwareList.add(cpu2);
        hardwareList.add(gpu1);
        hardwareList.add(gpu2);
        hardwareList.add(mbo1);
        hardwareList.add(ram1);
        hardwareList.add(storage1);
        hardwareList.add(other1);
    }

    @Override
    public List<Hardware> getAllHardware() {
        return hardwareList;
    }

    @Override
    public List<Hardware> getHardwareBySNum(String Snum) {
        return hardwareList.stream()
                .filter(h -> h.getSNum().equalsIgnoreCase(Snum))
                .collect(Collectors.toList());
    }

    @Override
    public Integer saveNewHardware(Hardware hardware) {
        Integer generatedID = hardwareList.size() + 1;
        hardware.setId(generatedID);
        hardwareList.add(hardware);
        return generatedID;
       }

    public Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Integer id) {
        Optional<Hardware> storedHardwareOptional = hardwareList.stream().filter(a -> a.getId().equals(id)).findFirst();
        if(storedHardwareOptional.isPresent()) {
            Hardware storedHardware = storedHardwareOptional.get();
            storedHardware.setName(hardwareToUpdate.getName());
            storedHardware.setType(hardwareToUpdate.getType());
            storedHardware.setPrice(hardwareToUpdate.getPrice());

            return Optional.of(storedHardware);
        }

        return Optional.empty();
    }

    @Override
    public boolean hardwareByIdExists(Integer id) {
//        return articleList.stream().filter(a -> a.getId().equals(id)).findFirst().isPresent();
        return hardwareList.stream()
                .anyMatch(a -> a.getId().equals(id));
    }

    @Override
    public boolean deleteHardwareById(Integer id) {
        return hardwareList.removeIf(article -> article.getId().equals(id));
    }
}







