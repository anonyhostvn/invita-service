package com.cmc.invitaservice.models.external.response;

import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import lombok.Data;

import java.util.List;

@Data
public class GetAllDocumentResponse {
    List <InvitaDocument> listDocument;
}
