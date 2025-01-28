package br.com.compass.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transfer")
public class Transfer {

    public enum TransferStatus {
        PENDING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destinationAccount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;

    @Column(length = 100)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Transfer() { }

    private Transfer(Builder builder) {
        this.sourceAccount = builder.sourceAccount;
        this.destinationAccount = builder.destinationAccount;
        this.amount = builder.amount;
        this.description = builder.description;
    }

    public Long getId() {
        return id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;
        return Objects.equals(getId(), transfer.getId()) &&
                Objects.equals(getSourceAccount(), transfer.getSourceAccount()) &&
                Objects.equals(getDestinationAccount(), transfer.getDestinationAccount()) &&
                Objects.equals(getAmount(), transfer.getAmount()) &&
                Objects.equals(getTimestamp(), transfer.getTimestamp()) &&
                getStatus() == transfer.getStatus() &&
                Objects.equals(getDescription(), transfer.getDescription()) &&
                Objects.equals(createdAt, transfer.createdAt) &&
                Objects.equals(updatedAt, transfer.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getSourceAccount());
        result = 31 * result + Objects.hashCode(getDestinationAccount());
        result = 31 * result + Objects.hashCode(getAmount());
        result = 31 * result + Objects.hashCode(getTimestamp());
        result = 31 * result + Objects.hashCode(getStatus());
        result = 31 * result + Objects.hashCode(getDescription());
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.timestamp = LocalDateTime.now();
        this.status = TransferStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public static class Builder {

        Account sourceAccount;
        Account destinationAccount;
        BigDecimal amount;
        LocalDateTime timestamp;
        String description;

        public Builder withSourceAccount(Account sourceAccount) {
            this.sourceAccount = sourceAccount;
            return this;
        }

        public Builder withDestinationAccount(Account destinationAccount) {
            this.destinationAccount = destinationAccount;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Transfer build() {
            return new Transfer(this);
        }

    }

}