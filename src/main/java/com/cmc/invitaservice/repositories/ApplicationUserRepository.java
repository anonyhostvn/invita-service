package com.cmc.invitaservice.repositories;


import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    @Override
    List<ApplicationUser> findAll();

    ApplicationUser findByEmail(String email);
    ApplicationUser findApplicationUserById(Long Id);
}
