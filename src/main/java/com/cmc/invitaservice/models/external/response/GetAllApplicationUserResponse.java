package com.cmc.invitaservice.models.external.response;

import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Data
public class GetAllApplicationUserResponse {
    @JsonIgnore
    List <ApplicationUser> listApplicationUser;
}
