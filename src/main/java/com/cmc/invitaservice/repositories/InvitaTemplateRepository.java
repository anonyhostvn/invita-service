package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitaTemplateRepository extends JpaRepository<InvitaTemplate, Long> {
    Optional<InvitaTemplate> findInvitaTemplateById(Long id);

    Optional<InvitaTemplate> findInvitaTemplateByTemplateName(String templateName);

}
