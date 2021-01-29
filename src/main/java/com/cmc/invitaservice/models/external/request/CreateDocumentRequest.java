package com.cmc.invitaservice.models.external.request;

import lombok.Data;

@Data
public class CreateDocumentRequest {
    private Long creatorId;
    private Long templateId;
    private Long ownerId;
    private String documentName;
    private String filledInformation;
    private String note;
}
