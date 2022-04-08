package com.example.springsecurity_exam.repository;

import com.example.springsecurity_exam.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
