package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateTemplateRequest;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;

public interface TemplateService {
    GetAllTemplateResponse getAllTemplate();
    void deleteTemplate(Long id);
    InvitaTemplate getTemplateByTemplateId(Long id);
    InvitaTemplate addTemplate(CreateTemplateRequest createTemplateRequest);
    void changeTemplate(CreateTemplateRequest createTemplateRequest, Long id);
}
