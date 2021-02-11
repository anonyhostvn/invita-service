package com.cmc.invitaservice.repositories;


import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    ApplicationUser findApplicationUserById(Long Id);
}
