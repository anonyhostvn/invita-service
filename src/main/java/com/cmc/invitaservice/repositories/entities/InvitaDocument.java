package com.cmc.invitaservice.repositories.entities;

import com.cmc.invitaservice.models.external.request.CreateDocumentRequest;
import com.cmc.invitaservice.models.external.request.UpdateDocumentRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invita_document")
public class InvitaDocument extends BaseEntity{
    private static final long serialVersionUID = 8346941764763055164L;

    @Column(name = "creator_id")
    private Long creatorId;

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    @JsonIgnore//Properties(value = "invitaDocumentList", allowSetters = true)
    private InvitaTemplate invitaTemplate;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "filled_information")
    private String filledInformation;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIgnore//Properties(value = "invitaDocumentList", allowSetters = true)
    private ApplicationUser applicationUser;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore//Properties(value = "invitaDocumentList", allowSetters = true)
    private InvitaDocument invitaDocument;

    @OneToMany(mappedBy = "invitaDocument", cascade = CascadeType.ALL)
    @JsonIgnore//Properties(value = "invitaDocument", allowSetters = true)
    private List<InvitaDocument> invitaDocumentList;

    public void setUpdateDocumentRequest(UpdateDocumentRequest updateDocumentRequest){
        this.documentName = updateDocumentRequest.getDocumentName();
        this.filledInformation = updateDocumentRequest.getFilledInformation();
        this.note = updateDocumentRequest.getNote();
    }

    public void setCreateDocumentRequest(CreateDocumentRequest createDocumentRequest){
        this.documentName = createDocumentRequest.getDocumentName();
        this.filledInformation = createDocumentRequest.getFilledInformation();
        this.note = createDocumentRequest.getNote();
    }
}
