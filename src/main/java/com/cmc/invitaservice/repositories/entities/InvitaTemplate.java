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
@Table(name = "invita_template")
public class InvitaTemplate extends BaseEntity {

    private static final long serialVersionUID = 4976088063801710628L;

    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(name = "template_content")
    private String templateContent;

    @Column(name = "note")
    private String note;

    @Column(name = "preview_img")
    private String previewImg;
}
