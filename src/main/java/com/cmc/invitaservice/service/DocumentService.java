package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;

import java.util.Optional;

public interface DocumentService {
    GetAllDocumentResponse getAllDocument();
    void deleteDocument(Long id);
    Optional<InvitaDocument> getDocumentById(Long Id);
    InvitaDocument addDocument(CreateDocumentRequest createDocumentRequest);
    void changeDocument(UpdateDocumentRequest updateDocumentRequest, Long Id);
}
