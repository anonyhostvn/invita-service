package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.InvitaTemplateRepository;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import com.cmc.invitaservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TemplateServiceImplement implements TemplateService {

    private InvitaTemplateRepository invitaTemplateRepository;

    @Autowired
    public TemplateServiceImplement(InvitaTemplateRepository invitaTemplateRepository) {
        this.invitaTemplateRepository = invitaTemplateRepository;
    }

    @Override
    public GetAllTemplateResponse getAllTemplate() {
        List<InvitaTemplate> invitaTemplateList = invitaTemplateRepository.findAll();

        GetAllTemplateResponse getAllTemplateResponse = new GetAllTemplateResponse();
        getAllTemplateResponse.setListTemplate(invitaTemplateList);

        return getAllTemplateResponse;
    }

    @Override
    public void deleteTemplate(Long id){
        invitaTemplateRepository.deleteById(id);
    }

    @Override
    public Optional<InvitaTemplate> getTemplateByName(String templateName){
        return invitaTemplateRepository.findInvitaTemplateByTemplateName(templateName);
    }

    @Override
    public void addTemplate(InvitaTemplate invitaTemplate){
        invitaTemplateRepository.save(invitaTemplate);
    }
}
