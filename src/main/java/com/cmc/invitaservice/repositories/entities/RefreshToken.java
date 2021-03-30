package com.cmc.invitaservice.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken{
    private static final long serialVersionUID = 4112347657184156156L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    @Column(nullable = false, name = "expiry_time")
    private Date expiryDate;

    public RefreshToken(String token, String username, Date date){
        this.token = token;
        this.username = username;
        this.expiryDate = date;
    }
}

