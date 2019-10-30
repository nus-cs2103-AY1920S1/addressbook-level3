package seedu.deliverymans.logic.parser;

/**
 * (to be added)
 */
public enum Context {
    GLOBAL,
    CUSTOMER,
    CUSTOMERLIST,
    DELIVERYMEN,
    RESTAURANT,
    DELIVERYMENSTATUS,
    EDITING,
    ORDER,
    DELIVERYMANRECORD;

    public String toLowerCaseString() {
        return name().toLowerCase();
    }
}
