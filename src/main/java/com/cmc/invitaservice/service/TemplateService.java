package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;

import java.util.Optional;

public interface TemplateService {
    GetAllTemplateResponse getAllTemplate();
    void deleteTemplate(Long id);
    Optional<InvitaTemplate> getTemplateByName(String templateName);
    void addTemplate(InvitaTemplate invitaTemplatee);
}
