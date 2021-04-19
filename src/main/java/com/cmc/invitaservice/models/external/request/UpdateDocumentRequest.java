package com.cmc.invitaservice.models.external.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NotBlank
public class UpdateDocumentRequest {
    private Long templateId;
    private String documentName;
    private String filledInformation;
    private String note;
}
