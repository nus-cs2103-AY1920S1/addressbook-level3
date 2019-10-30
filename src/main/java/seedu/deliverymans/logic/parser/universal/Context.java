package seedu.deliverymans.logic.parser.universal;

/**
 * (to be added)
 */
public enum Context {
    GLOBAL,
    CUSTOMER,
    CUSTOMERLIST,
    DELIVERYMEN,
    DELIVERYMENSTATUS,
    DELIVERYMENSTATISTICS,
    DELIVERYMANRECORD,
    RESTAURANT,
    EDITING,
    ORDER;

    public String toLowerCaseString() {
        return name().toLowerCase();
    }
}
