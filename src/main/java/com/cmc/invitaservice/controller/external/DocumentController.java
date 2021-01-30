package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/external", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/document")
    public ResponseEntity<GeneralResponse<GetAllDocumentResponse>> getAllDocument() {
        return ResponseFactory.success(documentService.getAllDocument());
    }

    @DeleteMapping("/document/{documentId}")
    public ResponseEntity deleteDocument(@PathVariable(name="documentId") Long documentId){
        documentService.deleteDocument(documentId);
        return ResponseFactory.success();
    }

    @GetMapping("/document/{documentId}")
    public  ResponseEntity getDocumentByName(@PathVariable(name="documentId") Long documentId){
        return ResponseFactory.success(documentService.getDocumentById(documentId));
    }

    @PostMapping("/document")
    public ResponseEntity addDocument(@RequestBody InvitaDocument invitaDocument){
        return ResponseFactory.success(documentService.addDocument(invitaDocument));
    }

    @PutMapping("/document/{documentId}")
    public  ResponseEntity editDocument(@PathVariable(name="documentId") Long documentId,
                                        @RequestBody CreateDocumentRequest createDocumentRequest) {
        if (documentService.getDocumentById(documentId).isPresent()) {
            documentService.changeDocument(createDocumentRequest, documentId);
            return ResponseFactory.success(documentService.getDocumentById(documentId));
        }
        return ResponseFactory.error(HttpStatus.valueOf("200"),ResponseStatusEnum.UNKNOWN_ERROR);
    }
}
