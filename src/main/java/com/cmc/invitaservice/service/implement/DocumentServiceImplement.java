package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.InvitaDocumentRepository;
import com.cmc.invitaservice.repositories.InvitaTemplateRepository;
import com.cmc.invitaservice.repositories.RefreshTokenRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DocumentServiceImplement implements DocumentService {
    private final InvitaDocumentRepository invitaDocumentRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final InvitaTemplateRepository invitaTemplateRepository;
    private final RoleService roleService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public DocumentServiceImplement(InvitaDocumentRepository invitaDocumentRepository,
                                    ApplicationUserRepository applicationUserRepository,
                                    InvitaTemplateRepository invitaTemplateRepository,
                                    RoleService roleService, RefreshTokenRepository refreshTokenRepository){
        this.invitaDocumentRepository = invitaDocumentRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.invitaTemplateRepository = invitaTemplateRepository;
        this.roleService = roleService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> getAllDocument(){
        List<InvitaDocument> invitaDocumentList;
        if (roleService.hasRole("ROLE_ADMIN")){
            if (refreshTokenRepository.findByUsername("admin") == null)
                return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
            invitaDocumentList = invitaDocumentRepository.findAll();
        }
        else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            if (refreshTokenRepository.findByUsername(username) == null)
                return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
            invitaDocumentList = invitaDocumentRepository.findInvitaDocumentByApplicationUserUsername(username);
        }
        GetAllDocumentResponse getAllDocumentResponse = new GetAllDocumentResponse();
        getAllDocumentResponse.setListDocument(invitaDocumentList);
        return ResponseFactory.success(getAllDocumentResponse);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteDocument(Long id) {
        if (roleService.hasRole("ROLE_ADMIN")) {
            if (refreshTokenRepository.findByUsername("admin") == null)
                return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
            invitaDocumentRepository.deleteById(id);
            return ResponseFactory.success("Delete successfully");
        }
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(id);
        if (invitaDocument == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_EXIST);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (refreshTokenRepository.findByUsername(username) == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
        if (invitaDocument.getApplicationUser().getUsername().equals(username)) {
            invitaDocumentRepository.deleteById(id);
            return ResponseFactory.success("Delete successfully");
        }
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> getDocumentById(Long documentId){
        if (roleService.hasRole("ROLE_ADMIN")) {
            if (refreshTokenRepository.findByUsername("admin") == null)
                return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
            return ResponseFactory.success(invitaDocumentRepository.findInvitaDocumentById(documentId));
        }
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(documentId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (refreshTokenRepository.findByUsername(username) == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
        if (invitaDocument.getApplicationUser().getUsername().equals(username))
            return ResponseFactory.success(invitaDocumentRepository.findInvitaDocumentById(documentId));
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> addDocument(CreateDocumentRequest createDocumentRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (refreshTokenRepository.findByUsername(username) == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        InvitaDocument invitaDocument = new InvitaDocument();
        invitaDocument.setInvitaTemplate(invitaTemplateRepository.findInvitaTemplateById(createDocumentRequest.getTemplateId()));
        invitaDocument.setApplicationUser(applicationUser);
        invitaDocument.setCreateDocumentRequest(createDocumentRequest);
        invitaDocumentRepository.save(invitaDocument);
        return ResponseFactory.success(invitaDocument);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> changeDocument(UpdateDocumentRequest updateDocumentRequest, Long documentId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (refreshTokenRepository.findByUsername(username) == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(documentId);
        if (invitaDocument.getApplicationUser().getUsername().equals(username)) {
            invitaDocument.setInvitaTemplate(invitaTemplateRepository.findInvitaTemplateById(updateDocumentRequest.getTemplateId()));
            invitaDocument.setUpdateDocumentRequest(updateDocumentRequest);
            invitaDocumentRepository.save(invitaDocument);
            return ResponseFactory.success(invitaDocument);
        }
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
    }
}
