package brightlogic.globapay.domain.model;

import jakarta.persistence.*;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
