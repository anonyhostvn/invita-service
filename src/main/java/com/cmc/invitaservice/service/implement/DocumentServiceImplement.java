package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.InvitaTemplateRepository;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import com.cmc.invitaservice.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocumentServiceImplement implements DocumentService {

    private InvitaTemplateRepository invitaTemplateRepository;

    @Autowired
    public DocumentServiceImplement(InvitaTemplateRepository invitaTemplateRepository) {
        this.invitaTemplateRepository = invitaTemplateRepository;
    }

    @Override
    public GetAllTemplateResponse getAllTemplate() {
        List<InvitaTemplate> invitaTemplateList = invitaTemplateRepository.findAll();

        GetAllTemplateResponse getAllTemplateResponse = new GetAllTemplateResponse();
        getAllTemplateResponse.setListTemplate(invitaTemplateList);

        return getAllTemplateResponse;
    }

}
