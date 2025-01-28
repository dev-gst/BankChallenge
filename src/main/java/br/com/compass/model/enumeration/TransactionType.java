package br.com.compass.model.enumeration;

public enum TransactionType {

    WITHDRAW(1, "Withdraw"),
    DEPOSIT(2, "Deposit"),
    TRANSFER(3, "Transfer");

    private final int code;
    private final String description;

    TransactionType(int code, String withdrawal) {
        this.code = code;
        this.description = withdrawal;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionType fromCode(int code) {
        for (TransactionType type : values()) {
            if (type.code == code) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid transaction type code.");
    }

    public static TransactionType fromDescription(String description) {
        for (TransactionType type : values()) {
            if (type.description.equalsIgnoreCase(description)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid transaction type description.");
    }

}
