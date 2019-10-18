package seedu.deliverymans.logic.parser.universal;

/**
 * (to be added)
 */
public enum Context {
    GLOBAL,
    CUSTOMER,
    DELIVERYMEN,
    RESTAURANT,
    EDITING;

    public String toLowerCaseString() {
        return name().toLowerCase();
    }
}
