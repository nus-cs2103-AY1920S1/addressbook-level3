package seedu.deliverymans.logic.parser.universal;

/*
 * (To be added)
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
