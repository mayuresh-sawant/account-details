package au.com.wholesale.account.details.domain.account;

public enum AccountType {
    SAVING("SAVING"),
    CURRENT("CURRENT");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
