package com.cmc.invitaservice.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "invita_document")
public class InvitaDocument extends BaseEntity{
    private static final long serialVersionUID = 8346941764763055164L;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "filled_information")
    private String filledInformation;

    @Column(name = "note")
    private String note;
}
