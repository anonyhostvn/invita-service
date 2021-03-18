package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.InvitaDocumentRepository;
import com.cmc.invitaservice.repositories.InvitaTemplateRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import com.cmc.invitaservice.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cmc.invitaservice.repositories.entities.ERole.ROLE_ADMIN;

@Service
@Slf4j
public class DocumentServiceImplement implements DocumentService {
    private final InvitaDocumentRepository invitaDocumentRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final InvitaTemplateRepository invitaTemplateRepository;

    @Autowired
    public DocumentServiceImplement(InvitaDocumentRepository invitaDocumentRepository,
                                    ApplicationUserRepository applicationUserRepository,
                                    InvitaTemplateRepository invitaTemplateRepository){
        this.invitaDocumentRepository = invitaDocumentRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.invitaTemplateRepository = invitaTemplateRepository;
    }

    @Override
    public GetAllDocumentResponse getAllDocument(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<InvitaDocument> invitaDocumentList;
        if (userDetails.getAuthorities().contains(ROLE_ADMIN)) {
            invitaDocumentList = invitaDocumentRepository.findAll();
            System.out.println(1);
        }
        else
            invitaDocumentList = invitaDocumentRepository.findInvitaDocumentByApplicationUserUsername(username);
        GetAllDocumentResponse getAllDocumentResponse = new GetAllDocumentResponse();
        getAllDocumentResponse.setListDocument(invitaDocumentList);
        return getAllDocumentResponse;
    }

    @Override
    public void deleteDocument(Long id){
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (invitaDocument.getApplicationUser().getUsername().equals(username)) invitaDocumentRepository.deleteById(id);
    }

    @Override
    public InvitaDocument getDocumentById(Long documentId){
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(documentId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (invitaDocument.getApplicationUser().getUsername().equals(username))
            return invitaDocumentRepository.findInvitaDocumentById(documentId);
        return null;
    }

    @Override
    public InvitaDocument addDocument(CreateDocumentRequest createDocumentRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        InvitaDocument invitaDocument = new InvitaDocument();
        invitaDocument.setInvitaTemplate(invitaTemplateRepository.findInvitaTemplateById(createDocumentRequest.getTemplateId()));
        invitaDocument.setApplicationUser(applicationUser);
        invitaDocument.setCreateDocumentRequest(createDocumentRequest);
        invitaDocumentRepository.save(invitaDocument);
        return invitaDocument;
    }

    @Override
    public void changeDocument(UpdateDocumentRequest updateDocumentRequest, Long documentId){
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(documentId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (invitaDocument.getApplicationUser().getUsername().equals(username)) {
            invitaDocument.setInvitaTemplate(invitaTemplateRepository.findInvitaTemplateById(updateDocumentRequest.getTemplateId()));
            invitaDocument.setUpdateDocumentRequest(updateDocumentRequest);
            invitaDocumentRepository.save(invitaDocument);
        }
    }
}
