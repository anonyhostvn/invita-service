package com.cmc.invitaservice.models.external.response;

import com.cmc.invitaservice.repositories.entities.InvitaTemplate;
import lombok.Data;

import java.util.List;

@Data
public class GetAllTemplateResponse {
    List<InvitaTemplate> listTemplate;

}
