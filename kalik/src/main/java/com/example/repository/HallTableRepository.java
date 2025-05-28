package com.example.repository;

import com.example.entity.HallTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallTableRepository extends JpaRepository<HallTable, Integer> {
}