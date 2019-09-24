package seedu.address.logic.parser;

public enum Context {
    RESTAURANT,
    CUSTOMER,
    DELIVERYMEN,
    GLOBAL;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
