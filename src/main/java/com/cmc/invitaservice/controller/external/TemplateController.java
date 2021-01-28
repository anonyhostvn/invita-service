package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/external/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateController {

    private TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/template")
    public ResponseEntity<GeneralResponse<GetAllTemplateResponse>> getAllTemplate() {
        return ResponseFactory.success(templateService.getAllTemplate());
    }

    @DeleteMapping("/template/{templateId}")
    public ResponseEntity deleteTemplate(@PathVariable (name = "templateId") Long templateId){
        templateService.deleteTemplate(templateId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/template/{templateName}")
    public ResponseEntity getTemplateByName(@PathVariable(name="templateName") String templateName){
        return ResponseFactory.success(templateService.getTemplateByName(templateName));
    }

    @PostMapping("/template/add")
    public ResponseEntity addTemplate(@RequestBody InvitaTemplate invitaTemplate){
        templateService.addTemplate(invitaTemplate);
        return ResponseEntity.ok().body(invitaTemplate);
    }

    @PutMapping("/template/{templateName}")
    public  ResponseEntity editTemplate(@PathVariable(name="templateName") String templateName,
                                        @RequestBody InvitaTemplate invitaTemplate) {
        Optional<InvitaTemplate> template = templateService.getTemplateByName(templateName);
        if (template.isPresent()) {
            invitaTemplate.setId(template.get().getId());
            templateService.addTemplate(invitaTemplate);
            return ResponseEntity.ok().body(invitaTemplate);
        }
        return ResponseEntity.badRequest().body("Bad");
    }
}
