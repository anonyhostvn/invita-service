package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitaDocumentRepository extends JpaRepository<InvitaDocument,Long> {
    Optional<InvitaDocument> findInvitaDocumentById(Long id);
}
