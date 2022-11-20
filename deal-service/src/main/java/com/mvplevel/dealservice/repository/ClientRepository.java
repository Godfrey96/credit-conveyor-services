package com.mvplevel.dealservice.repository;

import com.mvplevel.dealservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
