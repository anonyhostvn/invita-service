package com.cmc.invitaservice.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role extends  BaseEntity{
    private static final long serialVersionUID = -7945617237467342822L;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole name;

}
