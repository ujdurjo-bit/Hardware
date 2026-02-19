package hr.java.web.Hardware.controller;


import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.service.HardwareService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hardware")
@AllArgsConstructor
public class HardwareController {

    private HardwareService hardwareService;

    @GetMapping
    public List<HardwareDTO> getAllHardware() {
        return hardwareService.getAllHardware().stream().toList();
    }

    @GetMapping("/{Snum}")
    public List<HardwareDTO> getHardwareBySnum(@PathVariable String Snum) {
        return hardwareService.getHardwareBySNum(Snum).stream().toList();
    }


}
