package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateTemplateRequest;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.InvitaTemplateRepository;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import com.cmc.invitaservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<InvitaTemplate> getTemplateByTemplateId(Long templateId){
        return invitaTemplateRepository.findInvitaTemplateById(templateId);
    }

    @Override
    public InvitaTemplate addTemplate(CreateTemplateRequest createTemplateRequest){
        InvitaTemplate invitaTemplate = new InvitaTemplate();
        invitaTemplate.setCreateTemplateRequest(createTemplateRequest);
        invitaTemplateRepository.save(invitaTemplate);
        return invitaTemplate;
    }

    @Override
    public void changeTemplate(CreateTemplateRequest createTemplateRequest, Long templateId){
        InvitaTemplate invitaTemplate = invitaTemplateRepository.findInvitaTemplateById(templateId).get();
        invitaTemplate.setCreateTemplateRequest(createTemplateRequest);
        invitaTemplateRepository.save(invitaTemplate);
    }
}
