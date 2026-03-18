package hr.java.web.Hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.java.web.Hardware.dto.AuthRequestDTO;
import hr.java.web.Hardware.dto.HardwareDTO;
import hr.java.web.Hardware.dto.JwtResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql(scripts = {"/data.sql"})
class HardwareControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;

        AuthRequestDTO authRequest = new AuthRequestDTO();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin");

        ResponseEntity<JwtResponseDTO> response = restTemplate.postForEntity(
                baseUrl + "/auth/api/v1/login",
                authRequest,
                JwtResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        accessToken = response.getBody().getAccessToken();
    }

    private HttpEntity<?> createRequestEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<?> createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return new HttpEntity<>(headers);
    }

    @Test
    void testGetAll() {
        ResponseEntity<HardwareDTO[]> response = restTemplate.exchange(
                baseUrl + "/hardware",
                HttpMethod.GET,
                createRequestEntity(),
                HardwareDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        HardwareDTO[] hardware = response.getBody();
        assertThat(hardware).isNotNull();
        assertThat(hardware.length).isEqualTo(8);
        assertThat(hardware[0].getHardwareSerialNumber()).isEqualTo("CPU-001");
    }

    @Test
    void testGetByCode() {
        String code = "CPU-001";

        ResponseEntity<HardwareDTO[]> response = restTemplate.exchange(
                baseUrl + "/hardware/" + code,
                HttpMethod.GET,
                createRequestEntity(),
                HardwareDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        HardwareDTO[] hardware = response.getBody();
        assertThat(hardware).isNotNull();
        assertThat(hardware.length).isEqualTo(1);
        assertThat(hardware[0].getHardwareName()).isEqualTo("Intel Core i7-13700K");
        assertThat(hardware[0].getHardwareSerialNumber()).isEqualTo(code);
    }

    @Test
    void testSaveHardware() {
        HardwareDTO newHardware = new HardwareDTO(
                "Test Hardware",
                "TEST-001",
                new BigDecimal("99.99"),
                "OTHER",
                7
        );

        ResponseEntity<HardwareDTO> response = restTemplate.exchange(
                baseUrl + "/hardware/new",
                HttpMethod.POST,
                createRequestEntity(newHardware),
                HardwareDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        HardwareDTO saved = response.getBody();
        assertThat(saved).isNotNull();
        assertThat(saved.getHardwareName()).isEqualTo("Test Hardware");
        assertThat(saved.getHardwareSerialNumber()).isEqualTo("TEST-001");
    }

    @Test
    void testUpdateHardware() {
        Integer id = 1;
        HardwareDTO updateData = new HardwareDTO(
                "Updated CPU",
                "CPU-001",
                new BigDecimal("499.99"),
                "CPU",
                20
        );

        ResponseEntity<HardwareDTO> response = restTemplate.exchange(
                baseUrl + "/hardware/hardware/" + id,
                HttpMethod.PUT,
                createRequestEntity(updateData),
                HardwareDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        HardwareDTO updated = response.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getHardwareName()).isEqualTo("Updated CPU");
        assertThat(updated.getHardwarePrice()).isEqualByComparingTo("499.99");
    }

    @Test
    void testDeleteHardware() {
        Integer id = 2;

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/hardware/hardware/" + id,
                HttpMethod.DELETE,
                createRequestEntity(),
                Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}