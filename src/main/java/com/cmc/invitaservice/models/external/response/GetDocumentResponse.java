package com.cmc.invitaservice.models.external.response;

import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.repositories.entities.InvitaDocument;
import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetDocumentResponse implements Serializable {
    private static final long serialVersionUID = -3150145106556183813L;
    private Long creatorId;
    private String documentName;
    private String filledInformation;
    private String note;

    private InvitaTemplate invitaTemplate;

    private ApplicationUser applicationUser;

    private InvitaDocument parentDocument;

    private List<InvitaDocument> invitaDocumentList;

    public void setGetDocumentReponse(InvitaDocument invitaDocument){
        this.creatorId = invitaDocument.getCreatorId();
        this.documentName = invitaDocument.getDocumentName();
        this.note = invitaDocument.getNote();
        this.invitaTemplate = invitaDocument.getInvitaTemplate();
        this.applicationUser = invitaDocument.getApplicationUser();
        this.parentDocument = invitaDocument.getInvitaDocument();
        this.invitaDocumentList = invitaDocument.getInvitaDocumentList();
    }
}
