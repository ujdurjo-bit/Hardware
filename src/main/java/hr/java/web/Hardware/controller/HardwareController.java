

package hr.java.web.Hardware.controller;

import org.springframework.dao.DataIntegrityViolationException;
import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.service.HardwareService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        HardwareDTO savedHardwareDTO = hardwareService.saveNewHardware(hardwareDTO);
        return ResponseEntity.ok(savedHardwareDTO);
//        articleService.saveNewArticle(articleDTO);
//        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/hardware/{hardwareId}")
    public ResponseEntity<HardwareDTO> updateHardware(@Valid @RequestBody HardwareDTO hardwareDTO, @PathVariable Integer hardwareId) {
        Optional<HardwareDTO> updated = hardwareService.updateHardware(hardwareDTO, hardwareId);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConflict() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleNotFound(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}


