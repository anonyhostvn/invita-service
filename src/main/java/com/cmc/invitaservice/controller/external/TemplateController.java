package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.CreateTemplateRequest;
import com.cmc.invitaservice.models.external.response.GetAllTemplateResponse;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GeneralResponse<GetAllTemplateResponse>> getAllTemplate() {
        return ResponseFactory.success(templateService.getAllTemplate());
    }

    @DeleteMapping("/template/{templateId}")
    public ResponseEntity deleteTemplate(@PathVariable (name = "templateId") Long templateId){
        templateService.deleteTemplate(templateId);
        return ResponseFactory.success();
    }

    @GetMapping("/template/{templateId}")
    public ResponseEntity getTemplateById(@PathVariable(name="templateId") Long templateId){
        return ResponseFactory.success(templateService.getTemplateByTemplateId(templateId));
    }

    @PostMapping("/template/add")
    public ResponseEntity addTemplate(@RequestBody CreateTemplateRequest createTemplateRequest){
        templateService.addTemplate(createTemplateRequest);
        return ResponseFactory.success(templateService.getAllTemplate());
    }

    @PutMapping("/template/{templateId}")
    public  ResponseEntity editTemplate(@PathVariable(name="templateId") Long templateId,
                                        @RequestBody CreateTemplateRequest createTemplateRequest) {
        if (templateService.getTemplateByTemplateId(templateId).isPresent()){
            templateService.changeTemplate(createTemplateRequest, templateId);
            return ResponseFactory.success(templateService.getTemplateByTemplateId(templateId));
        }
        return ResponseEntity.badRequest().body("bad");
    }
}
