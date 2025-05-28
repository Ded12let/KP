package com.example.repository;

import com.example.entity.HookahOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HookahOrderRepository extends JpaRepository<HookahOrder, Long> {
}
