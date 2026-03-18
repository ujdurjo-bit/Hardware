package hr.java.web.Hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.service.HardwareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HardwareControllerTest {

    @Mock
    private HardwareService hardwareService;

    @InjectMocks
    private HardwareController hardwareController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private List<HardwareDTO> hardwareList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hardwareController).build();
        objectMapper = new ObjectMapper();

        HardwareDTO hardware1 = new HardwareDTO(
                "Hardware 1",
                "SN001",
                new BigDecimal("100.0"),
                "Category 1",
                10
        );
        HardwareDTO hardware2 = new HardwareDTO(
                "Hardware 2",
                "SN002",
                new BigDecimal("150.0"),
                "Category 2",
                5
        );
        hardwareList = Arrays.asList(hardware1, hardware2);
    }

    @Test
    void testGetAll() throws Exception {
        when(hardwareService.getAllHardware()).thenReturn(hardwareList);

        mockMvc.perform(get("/hardware")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].hardwareName", is("Hardware 1")))
                .andExpect(jsonPath("$[1].hardwareName", is("Hardware 2")));

        verify(hardwareService, times(1)).getAllHardware();
    }

    @Test
    void testGetByCode_Found() throws Exception {
        String code = "SN001";
        when(hardwareService.getHardwareBySNum(code)).thenReturn(List.of(hardwareList.get(0)));

        mockMvc.perform(get("/hardware/{Snum}", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].hardwareName", is("Hardware 1")));

        verify(hardwareService, times(1)).getHardwareBySNum(code);
    }

    @Test
    void testGetByCode_NotFound() throws Exception {
        String code = "NONEXISTENT";
        when(hardwareService.getHardwareBySNum(code)).thenReturn(List.of());

        mockMvc.perform(get("/hardware/{Snum}", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(hardwareService, times(1)).getHardwareBySNum(code);
    }

    @Test
    void testSave_Success() throws Exception {
        HardwareDTO newHardware = new HardwareDTO(
                "New Hardware",
                "SN003",
                new BigDecimal("200.0"),
                "Category 3",
                20
        );

        when(hardwareService.saveNewHardware(any(HardwareDTO.class))).thenReturn(newHardware);

        mockMvc.perform(post("/hardware/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newHardware)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hardwareName", is("New Hardware")))
                .andExpect(jsonPath("$.hardwareSerialNumber", is("SN003")));

        verify(hardwareService, times(1)).saveNewHardware(any(HardwareDTO.class));
    }

    @Test
    void testSave_Conflict() throws Exception {
        HardwareDTO newHardware = new HardwareDTO(
                "Duplicate Hardware",
                "SN001",
                new BigDecimal("200.0"),
                "Category 3",
                20
        );

        when(hardwareService.saveNewHardware(any(HardwareDTO.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        mockMvc.perform(post("/hardware/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newHardware)))
                .andExpect(status().isConflict());

        verify(hardwareService, times(1)).saveNewHardware(any(HardwareDTO.class));
    }

    @Test
    void testUpdate_Success() throws Exception {
        Integer id = 1;
        HardwareDTO updateData = new HardwareDTO(
                "Updated Hardware",
                "SN001",
                new BigDecimal("250.0"),
                "Category 1",
                15
        );

        when(hardwareService.hardwareByIdExists(id)).thenReturn(true);
        when(hardwareService.updateHardware(any(HardwareDTO.class), eq(id)))
                .thenReturn(Optional.of(updateData));

        mockMvc.perform(put("/hardware/hardware/{hardwareId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hardwareName", is("Updated Hardware")))
                .andExpect(jsonPath("$.hardwarePrice", is(250.0)));

        verify(hardwareService, times(1)).hardwareByIdExists(id);
        verify(hardwareService, times(1)).updateHardware(any(HardwareDTO.class), eq(id));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        Integer id = 999;
        HardwareDTO updateData = new HardwareDTO(
                "Nonexistent",
                "XXX",
                BigDecimal.ZERO,
                "Category X",
                0
        );

        when(hardwareService.hardwareByIdExists(id)).thenReturn(false);

        mockMvc.perform(put("/hardware/hardware/{hardwareId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());

        verify(hardwareService, times(1)).hardwareByIdExists(id);
        verify(hardwareService, never()).updateHardware(any(), anyInt());
    }

    @Test
    void testDelete() throws Exception {
        Integer id = 1;
        when(hardwareService.hardwareByIdExists(id)).thenReturn(true);
        when(hardwareService.deleteHardwareById(id)).thenReturn(true);

        mockMvc.perform(delete("/hardware/hardware/{hardwareId}", id))
                .andExpect(status().isOk());

        verify(hardwareService, times(1)).hardwareByIdExists(id);
        verify(hardwareService, times(1)).deleteHardwareById(id);
    }
}