package com.Boot.boot.repository;

import com.Boot.boot.entity.Role;
import com.Boot.boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
