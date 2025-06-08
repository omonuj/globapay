package brightlogic.globapay.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Entity(name = "user_account")
public class UserAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private BigDecimal walletBalance;

    private String phoneNumber;

    private String Country;

    private boolean isActive = true;

    public UserAccount(UUID userId, String fullName,
                       String email, BigDecimal walletBalance,
                       String phoneNumber, String country, boolean isActive) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.walletBalance = walletBalance;
        this.phoneNumber = phoneNumber;
        Country = country;
        this.isActive = isActive;
    }


    public UserAccount() {
    }

    public UserAccount(UUID userId) {
        this.userId = userId;
    }
}
