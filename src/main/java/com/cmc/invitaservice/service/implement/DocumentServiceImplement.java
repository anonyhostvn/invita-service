package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.models.external.response.GetDocumentResponse;
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
import com.cmc.invitaservice.service.config.RoleService;
import lombok.extern.slf4j.Slf4j;
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

    private String getUsername(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    private ResponseEntity<GeneralResponse<Object>> checkLogin(String username){
        if (refreshTokenRepository.findByUsername(username) == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
        return null;
    }

    private String GetAllFilledInfo(InvitaDocument invitaDocument){
        StringBuilder allFilledInfo = new StringBuilder();
        while (invitaDocument != null){
            allFilledInfo.insert(0, invitaDocument.getFilledInformation() + "\n");
            invitaDocument = invitaDocument.getInvitaDocument();
        }
        return allFilledInfo.toString();
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> getAllDocument(){
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        List<InvitaDocument> invitaDocumentList;
        if (roleService.hasRole("ROLE_ADMIN")) invitaDocumentList = invitaDocumentRepository.findAll();
        else invitaDocumentList = invitaDocumentRepository.findInvitaDocumentByApplicationUserUsername(username);
        GetAllDocumentResponse getAllDocumentResponse = new GetAllDocumentResponse();
        getAllDocumentResponse.setListDocument(invitaDocumentList);
        return ResponseFactory.success(getAllDocumentResponse);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteDocument(Long id) {
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(id);
        if (invitaDocument == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.DOCUMENT_EXIST);
        if (roleService.hasRole("ROLE_ADMIN")) {
            invitaDocumentRepository.deleteById(id);
            return ResponseFactory.success("Delete successfully");
        }
        if (invitaDocument.getApplicationUser().getUsername().equals(username)) {
            invitaDocumentRepository.deleteById(id);
            return ResponseFactory.success("Delete successfully");
        }
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> getDocumentById(Long documentId){
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(documentId);
        if (roleService.hasRole("ROLE_ADMIN") || invitaDocument.getApplicationUser().getUsername().equals(username)) {
            String allFilledInfor = GetAllFilledInfo(invitaDocument);
            GetDocumentResponse getDocumentResponse = new GetDocumentResponse();
            getDocumentResponse.setGetDocumentReponse(invitaDocument);
            getDocumentResponse.setFilledInformation(allFilledInfor);
            return ResponseFactory.success(getDocumentResponse);
        }
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
    }

    private InvitaDocument getParent(Long id){
        return invitaDocumentRepository.findInvitaDocumentById(id);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> addDocument(CreateDocumentRequest createDocumentRequest){
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        Long parentId = createDocumentRequest.getParentId();
        InvitaDocument parentDocument = getParent(parentId);
        if (parentId != null && parentDocument == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.DOCUMENT_EXIST);
        InvitaDocument invitaDocument = new InvitaDocument();
        invitaDocument.setInvitaTemplate(invitaTemplateRepository.findInvitaTemplateById(createDocumentRequest.getTemplateId()));
        invitaDocument.setApplicationUser(applicationUser);
        invitaDocument.setInvitaDocument(parentDocument);
        invitaDocument.setCreateDocumentRequest(createDocumentRequest);
        invitaDocument.setCreatorId(applicationUser.getId());
        invitaDocumentRepository.save(invitaDocument);
        return ResponseFactory.success(invitaDocument);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> changeDocument(UpdateDocumentRequest updateDocumentRequest, Long documentId){
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        Long parentId = updateDocumentRequest.getParentId();
        InvitaDocument parentDocument = getParent(parentId);
        if (parentId != null && (parentDocument == null || parentId.equals(documentId)))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.DOCUMENT_EXIST);

        InvitaDocument invitaDocument = invitaDocumentRepository.findInvitaDocumentById(documentId);
        if (invitaDocument.getApplicationUser().getUsername().equals(username) || roleService.hasRole("ROLE_ADMIN")) {
            invitaDocument.setInvitaTemplate(invitaTemplateRepository.findInvitaTemplateById(updateDocumentRequest.getTemplateId()));
            invitaDocument.setUpdateDocumentRequest(updateDocumentRequest);
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            invitaDocument.setCreatorId(applicationUser.getId());
            invitaDocument.setInvitaDocument(parentDocument);
            invitaDocumentRepository.save(invitaDocument);
            return ResponseFactory.success(invitaDocument);
        }
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
    }
}
