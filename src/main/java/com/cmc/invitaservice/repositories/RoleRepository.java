package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.ERole;
import com.cmc.invitaservice.repositories.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
