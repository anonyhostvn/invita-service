package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.InvitaDocumentRepository;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
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
    private InvitaDocumentRepository invitaDocumentRepository;

    @Autowired
    public DocumentServiceImplement(InvitaDocumentRepository invitaDocumentRepository){
        this.invitaDocumentRepository = invitaDocumentRepository;
    }

    @Override
    public GetAllDocumentResponse getAllDocument(){
        List<InvitaDocument> invitaDocumentList=invitaDocumentRepository.findAll();

        GetAllDocumentResponse getAllDocumentResponse = new GetAllDocumentResponse();
        getAllDocumentResponse.setListDocument(invitaDocumentList);

        return getAllDocumentResponse;
    }

    @Override
    public void deleteDocument(Long id){
        invitaDocumentRepository.deleteById(id);
    }

    @Override
    public Optional<InvitaDocument> getDocumentByName(String documentName){
        return  invitaDocumentRepository.findInvitaDocumentByDocumentName(documentName);
    }

    @Override
    public void addDocument(InvitaDocument invitaDocument){
        invitaDocumentRepository.save(invitaDocument);
    }
}