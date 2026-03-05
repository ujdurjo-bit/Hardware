package hr.java.web.Hardware.repository;


import hr.java.web.Hardware.domain.Hardware;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public interface SpringDataJpaHardwareRepository
        extends JpaRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {

    List<Hardware> findBySNum(String snum);
}
