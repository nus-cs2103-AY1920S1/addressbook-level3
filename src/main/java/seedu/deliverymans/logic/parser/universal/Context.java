package seedu.deliverymans.logic.parser.universal;

/**
 * (to be added)
 */
public enum Context {
    GLOBAL,
    CUSTOMER,
    DELIVERYMEN,
    RESTAURANT;

    public String toLowerCaseString() {
        return name().toLowerCase();
    }
}
