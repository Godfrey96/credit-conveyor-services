package com.mvplevel.dealservice.repository;

import com.mvplevel.dealservice.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
