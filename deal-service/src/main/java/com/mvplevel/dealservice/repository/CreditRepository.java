package com.mvplevel.dealservice.repository;

import com.mvplevel.dealservice.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
