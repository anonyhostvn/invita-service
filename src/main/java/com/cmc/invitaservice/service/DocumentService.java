package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.response.GetAllDocumentResponse;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;

import java.util.Optional;

public interface DocumentService {
    GetAllDocumentResponse getAllDocument();
    void deleteDocument(Long id);
    Optional<InvitaDocument> getDocumentByName(String documentName);
    void addDocument(InvitaDocument invitaDocument);
}