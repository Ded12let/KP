package com.example.repository;

import com.example.entity.Additive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface AdditiveRepository extends JpaRepository<Additive, Long> {
} 