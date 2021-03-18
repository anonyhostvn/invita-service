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
@PreAuthorize("hasRole('ROLE_USER')")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/document")
    public ResponseEntity<GeneralResponse<GetAllDocumentResponse>> getAllDocument() {
        return ResponseFactory.success(documentService.getAllDocument());
    }

    @DeleteMapping("/document/{documentId}")
    public ResponseEntity<GeneralResponse<Object>> deleteDocument(@PathVariable(name="documentId") Long documentId){
        documentService.deleteDocument(documentId);
        return ResponseFactory.success();
    }

    @GetMapping("/document/{documentId}")
    public  ResponseEntity<GeneralResponse<Object>> getDocumentByName(@PathVariable(name="documentId") Long documentId){
        return ResponseFactory.success(documentService.getDocumentById(documentId));
    }

    @PostMapping("document")
    public ResponseEntity<GeneralResponse<Object>> addDocument(@RequestBody CreateDocumentRequest createDocumentRequest){
        return ResponseFactory.success(documentService.addDocument(createDocumentRequest));
    }

    @PutMapping("/document/{documentId}")
    public  ResponseEntity<GeneralResponse<Object>> editDocument(@PathVariable(name="documentId") Long documentId,
                                        @RequestBody UpdateDocumentRequest updateDocumentRequest) {
            documentService.changeDocument(updateDocumentRequest, documentId);
            return ResponseFactory.success(documentService.getDocumentById(documentId));
    }
}
