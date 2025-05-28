package com.example.repository;

import com.example.entity.Booking;
import com.example.entity.HallTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    boolean existsByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalDateTime endTime, LocalDateTime startTime);

    List<Booking> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByHallTableIdAndStartTimeLessThanAndEndTimeGreaterThan(Integer tableId, LocalDateTime endTime, LocalDateTime startTime);

    boolean existsByHallTableIdAndStartTimeLessThanAndEndTimeGreaterThanEqual(Integer tableId, LocalDateTime endTime, LocalDateTime startTime);

    List<Booking> findAllByUserIdOrderByStartTimeDesc(Integer userId);
}