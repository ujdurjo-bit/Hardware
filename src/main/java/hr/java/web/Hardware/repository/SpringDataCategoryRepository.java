package hr.java.web.Hardware.repository;

import hr.java.web.Hardware.domain.HardwareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<HardwareType, Integer> {
    HardwareType findByName(String name);
}
