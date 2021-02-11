package com.cmc.invitaservice.models.external.response;

import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import lombok.Data;

import java.util.List;

@Data
public class GetAllApplicationUserResponse {
    List <ApplicationUser> listApplicationUser;
}
