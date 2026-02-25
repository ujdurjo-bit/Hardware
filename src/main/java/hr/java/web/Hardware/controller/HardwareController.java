/*Proširiti rješenje iz druge vježbe te implementirati sve preostale metode REST API sučelja kako je demonstrirano: GET, POST, PUT i DELETE.
        Dodati sve potrebne ovisnosti u "pom.xml" kao što je "spring-boot-starter-validation" u "pom.xml" datoteku.
        Proširiti HardwareDTO klasu s validacijskim anotacijama kako bi se validirala ispravnost poslanih podataka.
        Napisati POST, PUT i DELETE metode koje će upravljati podacima entiteta.
        Dodatne metode potrebno je implementirati po sva tri sloja aplikacije: "controller", "service" i "repository".*/

package hr.java.web.Hardware.controller;


import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.service.HardwareService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hardware")
@AllArgsConstructor
public class HardwareController {

    private HardwareService hardwareService;

    @GetMapping
    public ResponseEntity<List<HardwareDTO>> getAllHardware() {
        return ResponseEntity.ok(hardwareService.getAllHardware().stream().toList());
    }

    @GetMapping("/{Snum}")
    public ResponseEntity<List<HardwareDTO>> getHardwareBySnum(@PathVariable String Snum) {
        return ResponseEntity.ok(hardwareService.getHardwareBySNum(Snum).stream().toList());
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveNewAHardware(@Valid @RequestBody HardwareDTO hardwareDTO) {
        Integer generatedId = hardwareService.saveNewHardware(hardwareDTO);
        return ResponseEntity.ok(generatedId);
//        articleService.saveNewArticle(articleDTO);
//        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/hardware/{hardwareId}")
    public ResponseEntity<HardwareDTO> updateHardware(@Valid @RequestBody HardwareDTO hardwareDTO, @PathVariable Integer hardwareId) {
        if(hardwareService.hardwareByIdExists(hardwareId)) {
            hardwareService.updateHardware(hardwareDTO, hardwareId);
            return ResponseEntity.ok(hardwareDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/hardware/{hardwareId}")
    public ResponseEntity<?> deleteHardware(@PathVariable Integer hardwareId) {
        if(hardwareService.hardwareByIdExists(hardwareId)) {
            boolean result = hardwareService.deleteHardwareById(hardwareId);
            if(result) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}


