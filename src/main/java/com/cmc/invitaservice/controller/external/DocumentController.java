package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/external", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/document")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponse<GetAllDocumentResponse>> getAllDocument() {
        return ResponseFactory.success(documentService.getAllDocument());
    }

    @DeleteMapping("/document/{documentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponse<Object>> deleteDocument(@PathVariable(name="documentId") Long documentId){
        return documentService.deleteDocument(documentId);
    }

    @GetMapping("/document/{documentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public  ResponseEntity<GeneralResponse<Object>> getDocumentByName(@PathVariable(name="documentId") Long documentId){
        return documentService.getDocumentById(documentId);
    }

    @PostMapping("document")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GeneralResponse<Object>> addDocument(@RequestBody CreateDocumentRequest createDocumentRequest){
        return documentService.addDocument(createDocumentRequest);
    }

    @PutMapping("/document/{documentId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public  ResponseEntity<GeneralResponse<Object>> editDocument(@PathVariable(name="documentId") Long documentId,
                                        @RequestBody UpdateDocumentRequest updateDocumentRequest) {
            return documentService.changeDocument(updateDocumentRequest, documentId);
    }
}
