package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvitaTemplateRepository extends JpaRepository<InvitaTemplate, Long> {
    InvitaTemplate findInvitaTemplateById(Long id);
}
