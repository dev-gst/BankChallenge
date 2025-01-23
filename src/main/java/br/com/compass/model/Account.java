package br.com.compass.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "account")
@SuppressWarnings("unused")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Account() { }

    private Account(Builder builder) {
        this.client = builder.client;
        this.balance = builder.balance;
        this.type = builder.type;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        private BigDecimal balance;
        private String type;

        public Builder withClientId(Client client) {
            this.client = client;
            return this;
        }

        public Builder withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Account build() {
            return new Account(this);
        }

    }

}