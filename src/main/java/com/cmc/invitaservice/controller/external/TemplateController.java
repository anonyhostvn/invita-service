package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.CreateTemplateRequest;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/external", produces = MediaType.APPLICATION_JSON_VALUE)

public class TemplateController {

    private TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/template")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponse<GetAllTemplateResponse>> getAllTemplate() {
        return ResponseFactory.success(templateService.getAllTemplate());
    }

    @DeleteMapping("/template/{templateId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteTemplate(@PathVariable (name = "templateId") Long templateId){
        templateService.deleteTemplate(templateId);
        return ResponseFactory.success();
    }

    @GetMapping("/template/{templateId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity getTemplateById(@PathVariable(name="templateId") Long templateId){
        return ResponseFactory.success(templateService.getTemplateByTemplateId(templateId));
    }

    @PostMapping("/template")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity addTemplate(@RequestBody CreateTemplateRequest createTemplateRequest){
        return ResponseFactory.success(templateService.addTemplate(createTemplateRequest));
    }

    @PutMapping("/template/{templateId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public  ResponseEntity editTemplate(@PathVariable(name="templateId") Long templateId,
                                        @RequestBody CreateTemplateRequest createTemplateRequest) {
        if (templateService.getTemplateByTemplateId(templateId) != null){
            templateService.changeTemplate(createTemplateRequest, templateId);
            return ResponseFactory.success(templateService.getTemplateByTemplateId(templateId));
        }
        return ResponseFactory.error(HttpStatus.valueOf("200"), ResponseStatusEnum.UNKNOWN_ERROR);
    }
}
