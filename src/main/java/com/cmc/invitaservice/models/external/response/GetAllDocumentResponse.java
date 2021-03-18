package com.cmc.invitaservice.models.external.response;

import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Data
public class GetAllDocumentResponse {
    @JsonIgnore
    List <InvitaDocument> listDocument;
}
