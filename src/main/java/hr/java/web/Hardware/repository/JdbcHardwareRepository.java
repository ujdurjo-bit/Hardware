package hr.java.web.Hardware.repository;


import hr.java.web.Hardware.domain.Hardware;
import hr.java.web.Hardware.domain.HardwareType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class JdbcHardwareRepository implements HardwareRepository {

    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Hardware> getAllHardware() {
        return jdbcTemplate.query("SELECT * FROM HARDWARE", new HardwareMapper());
    }

    @Override
    public List<Hardware> getHardwareBySNum(String SNum) {
        final String SQL = "SELECT * FROM HARDWARE WHERE snum = ?";
        return jdbcTemplate.query(SQL, new HardwareMapper(), SNum);
    }



/*    @Override
    public Optional<Hardware> getHardwareBySNum(String SNum) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hardwareId", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM HARDWARE WHERE ID = :hardwareId",
                new HardwareMapper(), parameters));
}*/

    @Override
    public Integer saveNewHardware(Hardware hardware) {
        final String SQL =
                "SELECT ID FROM FINAL TABLE (INSERT INTO HARDWARE (name, snum, price, type, quantity) VALUES (?, ?, ?, ?, ?))";
        Integer generatedId = jdbcTemplate.queryForObject(SQL, Integer.class,
                hardware.getName(), hardware.getSNum(), hardware.getPrice(),
                hardware.getType().name(), hardware.getQuantity());
        hardware.setId(generatedId.longValue());
        return generatedId;
    }

    @Override
    public Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Integer id) {
        if (hardwareByIdExists(id)) {
            final String SQL =
                    "UPDATE HARDWARE SET name = ?, snum = ?, price = ?, type = ?, quantity = ? WHERE ID = ?";
            jdbcTemplate.update(SQL,
                    hardwareToUpdate.getName(),
                    hardwareToUpdate.getSNum(),
                    hardwareToUpdate.getPrice(),
                    hardwareToUpdate.getType().name(),
                    hardwareToUpdate.getQuantity(),
                    id
            );

            hardwareToUpdate.setId(id.longValue());
            return Optional.of(hardwareToUpdate);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean hardwareByIdExists(Integer id) {
        String SQL = "SELECT COUNT(*) FROM HARDWARE WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(SQL, Integer.class, id);
        return count > 0;
    }

    @Override
    public boolean deleteHardwareById(Integer id) {
        if (hardwareByIdExists(id)) {
            String SQL = "DELETE FROM HARDWARE WHERE id = ?";
            jdbcTemplate.update(SQL, id);
            return true;
        }
        return false;
    }

    private static class HardwareMapper implements RowMapper<Hardware> {

        public Hardware mapRow(ResultSet rs, int i) throws SQLException {

            Hardware newHardware = new Hardware();
            newHardware.setId(rs.getLong("ID"));
            newHardware.setName(rs.getString("NAME"));
            newHardware.setSNum(rs.getString("SNUM"));
            newHardware.setPrice(rs.getBigDecimal("PRICE"));
            newHardware.setType(HardwareType.valueOf(rs.getString("TYPE")));
            newHardware.setQuantity(rs.getInt("QUANTITY"));


            return newHardware;
        }

    }

}




