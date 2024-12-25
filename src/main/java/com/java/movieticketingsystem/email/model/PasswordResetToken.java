package com.java.movieticketingsystem.email.model;

import com.java.movieticketingsystem.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    @Column(name = "expiry_date")
    private Date expiryDate;
    public PasswordResetToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryDate = new Date(System.currentTimeMillis() + 1800000); // 30 minutes
    }
    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}
