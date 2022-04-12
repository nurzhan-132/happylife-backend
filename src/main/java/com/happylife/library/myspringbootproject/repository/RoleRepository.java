package com.happylife.library.myspringbootproject.repository;

import com.happylife.library.myspringbootproject.models.ERole;
import com.happylife.library.myspringbootproject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
