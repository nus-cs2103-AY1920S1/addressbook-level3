package seedu.deliverymans.logic.parser.universal;

/**
 * (to be added)
 */
public enum Context {
    GLOBAL,
    CUSTOMER,
    DELIVERYMEN,
    RESTAURANT,
    DELIVERYMENSTATUS,
    DELIVERYMANRECORD,
    EDITING;

    public String toLowerCaseString() {
        return name().toLowerCase();
    }
}
