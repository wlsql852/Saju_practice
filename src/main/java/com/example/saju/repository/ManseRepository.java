package com.example.saju.repository;

import com.example.saju.entity.Manse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ManseRepository extends JpaRepository<Manse,Integer> {
    Manse findBySolarDate(LocalDate solarDate);
    Manse findByLunarDate(LocalDate lunarDate);

    Optional<Manse> findFirstBySeasonStartTimeGreaterThanEqualOrderBySolarDateAsc(LocalDateTime solarDateTime);
    Optional<Manse> findFirstBySeasonStartTimeLessThanEqualOrderBySolarDateDesc(LocalDateTime solarDateTime);
}
