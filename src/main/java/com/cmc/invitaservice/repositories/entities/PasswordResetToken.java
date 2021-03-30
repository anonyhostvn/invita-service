package com.cmc.invitaservice.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetToken{
    private static final long serialVersionUID = -1088727601152369294L;

    public static final int EXPIRATION_TIME_RESET = 5;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @OneToOne(targetEntity = ApplicationUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;

    @Column(nullable = false, name = "expiry_time")
    private Date expiryDate;

    public PasswordResetToken(String token, ApplicationUser applicationUser){
        this.token = token;
        this.applicationUser = applicationUser;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION_TIME_RESET);
        return new Date(cal.getTime().getTime());
    }
}
