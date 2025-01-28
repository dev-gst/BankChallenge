package br.com.compass.model;
import br.com.compass.model.enumeration.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "transfer")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Transaction() { }

    private Transaction(Builder builder) {
        this.sourceAccount = builder.sourceAccount;
        this.destinationAccount = builder.destinationAccount;
        this.amount = builder.amount;
        this.type = builder.type;
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

    public Instant getTimestamp() {
        return createdAt;
    }

    private TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    private Instant createdAt() {
        return createdAt;
    }

    private Instant updatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Transaction transaction = (Transaction) o;
        return Objects.equals(getId(), transaction.getId()) &&
                Objects.equals(getSourceAccount(), transaction.getSourceAccount()) &&
                Objects.equals(getDestinationAccount(), transaction.getDestinationAccount()) &&
                Objects.equals(getAmount(), transaction.getAmount()) &&
                Objects.equals(getType(), transaction.getType()) &&
                Objects.equals(createdAt, transaction.createdAt) &&
                Objects.equals(updatedAt, transaction.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getSourceAccount());
        result = 31 * result + Objects.hashCode(getDestinationAccount());
        result = 31 * result + Objects.hashCode(getAmount());
        result = 31 * result + Objects.hashCode(getType());
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public static class Builder {

        Account sourceAccount;
        Account destinationAccount;
        BigDecimal amount;
        TransactionType type;

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

        public Builder withType(TransactionType type) {
            this.type = type;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }

    }

}