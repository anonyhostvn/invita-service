package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.response.GeneralResponse;
import org.springframework.http.ResponseEntity;

public interface DocumentService {
    GetAllDocumentResponse getAllDocument();
    ResponseEntity<GeneralResponse<Object>> deleteDocument(Long id);
    ResponseEntity<GeneralResponse<Object>> getDocumentById(Long Id);
    ResponseEntity<GeneralResponse<Object>> addDocument(CreateDocumentRequest createDocumentRequest);
    ResponseEntity<GeneralResponse<Object>> changeDocument(UpdateDocumentRequest updateDocumentRequest, Long Id);
}
