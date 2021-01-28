package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/external/document", produces = MediaType.APPLICATION_JSON_VALUE)
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
        return ResponseEntity.ok().build();
    }

    @GetMapping("/document/{documentName}")
    public  ResponseEntity getDocumentByName(@PathVariable(name="documentName") String documentName){
        return ResponseFactory.success(documentService.getDocumentByName(documentName));
    }

    @PostMapping("/document/add")
    public ResponseEntity addDocument(@RequestBody InvitaDocument invitaDocument){
        documentService.addDocument(invitaDocument);
        return ResponseEntity.ok().body(invitaDocument);
    }

    @PutMapping("/document/{documentName}")
    public  ResponseEntity editDocument(@PathVariable(name="documentName") String documentName,
                                        @RequestBody InvitaDocument invitaDocument) {
        Optional<InvitaDocument> document = documentService.getDocumentByName(documentName);
        if (document.isPresent()) {
            invitaDocument.setId(document.get().getId());
            documentService.addDocument(invitaDocument);
            return ResponseEntity.ok().body(invitaDocument);
        }
        return ResponseEntity.badRequest().body("Bad");
    }
}
