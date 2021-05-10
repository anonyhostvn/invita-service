package com.cmc.invitaservice.repositories.entities;


import com.cmc.invitaservice.models.external.request.CreateTemplateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invitaTemplate", cascade = CascadeType.ALL)
    @JsonIgnore//Properties(value = "invitaTemplate", allowSetters = true)
    private List<InvitaDocument> invitaDocumentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore//Properties(value = "invitaTemplateList", allowSetters = true)
    private InvitaTemplate invitaTemplate;

    @OneToMany(mappedBy = "invitaTemplate", cascade = CascadeType.ALL)
    @JsonIgnore//Properties(value = "invitaTemplate", allowSetters = true)
    private List<InvitaTemplate> invitaTemplateList;

    public void setCreateTemplateRequest(CreateTemplateRequest createTemplateRequest){
        this.templateName = createTemplateRequest.getTemplateName();
        this.templateContent = createTemplateRequest.getTemplateContent();
        this.note = createTemplateRequest.getNote();
        this.previewImg = createTemplateRequest.getPreviewImg();
    }
}
