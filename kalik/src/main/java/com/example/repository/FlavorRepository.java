package com.example.repository;

import com.example.entity.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Long> {
} 