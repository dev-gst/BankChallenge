package br.com.compass.model.enumeration;

public enum AccountType {

    CHECKING(1, "Checking"),
    SAVINGS(2, "Savings"),
    SALARY(3, "Salary");

    private final int code;
    private final String description;

    AccountType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AccountType fromCode(int code) {
        for (AccountType type : values()) {
            if (type.code == code) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid account type code.");
    }

    public static AccountType fromDescription(String description) {
        for (AccountType type : values()) {
            if (type.description.equalsIgnoreCase(description)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid account type description.");
    }

}
