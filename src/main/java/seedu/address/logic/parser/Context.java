package seedu.address.logic.parser;

/*
 * (To be added)
 */
public enum Context {
    RESTAURANT,
    CUSTOMER,
    DELIVERYMEN,
    GLOBAL;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
