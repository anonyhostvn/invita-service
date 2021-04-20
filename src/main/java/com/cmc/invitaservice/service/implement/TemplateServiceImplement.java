package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateTemplateRequest;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.InvitaTemplateRepository;
import com.cmc.invitaservice.repositories.RefreshTokenRepository;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.TemplateService;
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
public class TemplateServiceImplement implements TemplateService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final InvitaTemplateRepository invitaTemplateRepository;

    @Autowired
    public TemplateServiceImplement(InvitaTemplateRepository invitaTemplateRepository, RefreshTokenRepository refreshTokenRepository) {
        this.invitaTemplateRepository = invitaTemplateRepository;
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

    @Override
    public ResponseEntity<GeneralResponse<Object>> getAllTemplate() {
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        List<InvitaTemplate> invitaTemplateList = invitaTemplateRepository.findAll();
        GetAllTemplateResponse getAllTemplateResponse = new GetAllTemplateResponse();
        getAllTemplateResponse.setListTemplate(invitaTemplateList);
        return ResponseFactory.success(getAllTemplateResponse);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteTemplate(Long id){
        ResponseEntity<GeneralResponse<Object>> check = checkLogin("admin");
        if (check != null) return  check;
        invitaTemplateRepository.deleteById(id);
        return ResponseFactory.success();
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> getTemplateByTemplateId(Long templateId){
        String username = getUsername();
        ResponseEntity<GeneralResponse<Object>> check = checkLogin(username);
        if (check != null) return  check;
        InvitaTemplate invitaTemplate = invitaTemplateRepository.findInvitaTemplateById(templateId);
        return ResponseFactory.success(invitaTemplate);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> addTemplate(CreateTemplateRequest createTemplateRequest){
        ResponseEntity<GeneralResponse<Object>> check = checkLogin("admin");
        if (check != null) return  check;
        InvitaTemplate invitaTemplate = new InvitaTemplate();
        invitaTemplate.setCreateTemplateRequest(createTemplateRequest);
        invitaTemplateRepository.save(invitaTemplate);
        return ResponseFactory.success(invitaTemplate);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> changeTemplate(CreateTemplateRequest createTemplateRequest, Long templateId){
        ResponseEntity<GeneralResponse<Object>> check = checkLogin("admin");
        if (check != null) return  check;
        InvitaTemplate invitaTemplate = invitaTemplateRepository.findInvitaTemplateById(templateId);
        if (invitaTemplate == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.UNKNOWN_ERROR);
        invitaTemplate.setCreateTemplateRequest(createTemplateRequest);
        invitaTemplateRepository.save(invitaTemplate);
        return ResponseFactory.success(invitaTemplate);
    }
}
