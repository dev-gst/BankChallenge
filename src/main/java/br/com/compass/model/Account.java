package br.com.compass.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "account")
@SuppressWarnings("unused")
public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        SALARY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @Column(name = "account_number", nullable = false, length = 20)
    private String accountNumber;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType accountType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Account() { }

    private Account(Builder builder) {
        this.client = builder.client;
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = builder.balance;
        this.accountType = builder.accountType;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return accountType;
    }

    public void setType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        return Objects.equals(getId(), account.getId()) &&
                Objects.equals(getClient(), account.getClient()) &&
                Objects.equals(getBalance(), account.getBalance()) &&
                Objects.equals(getType(), account.getType()) &&
                Objects.equals(getCreatedAt(), account.getCreatedAt()) &&
                Objects.equals(getUpdatedAt(), account.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getClient());
        result = 31 * result + Objects.hashCode(getBalance());
        result = 31 * result + Objects.hashCode(getType());
        result = 31 * result + Objects.hashCode(getCreatedAt());
        result = 31 * result + Objects.hashCode(getUpdatedAt());
        return result;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public static class Builder {

        private Client client;
        private String accountNumber;
        private BigDecimal balance;
        private AccountType accountType;

        public Builder withClient(Client client) {
            this.client = client;
            return this;
        }

        public Builder withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder withType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Account build() {
            return new Account(this);
        }

    }

}