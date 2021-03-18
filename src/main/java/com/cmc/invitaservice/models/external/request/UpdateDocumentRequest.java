package com.cmc.invitaservice.models.external.request;

import lombok.Data;

@Data
public class UpdateDocumentRequest {
    private Long creatorId;
    private Long templateId;
    private String documentName;
    private String filledInformation;
    private String note;
}
