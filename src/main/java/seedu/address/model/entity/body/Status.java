package seedu.address.model.entity.body;

//@@author ambervoong
/**
 * Enumerates all the possible states a body undergoes in the mortuary.
 */
public enum Status {
    ARRIVED, PENDING_IDENTIFICATION, PENDING_CLAIM, CLAIMED, DONATED, PENDING_POLICE_REPORT, PENDING_CORONER_APPROVAL;

    /**
     * Parses {@code String status} to return the corresponding {@code Status}.
     */
    public static Status parseStatus(String status) {
        assert(status != null);
        String statusLowerCaps = status.toLowerCase();
        switch(statusLowerCaps) {
        case "arrived":
            return ARRIVED;
        case "pending identification":
            return PENDING_IDENTIFICATION;
        case "pending claim":
            return PENDING_CLAIM;
        case "pending coroner approval":
            return PENDING_CORONER_APPROVAL;
        case "pending police report":
            return PENDING_POLICE_REPORT;
        case "claimed":
            return CLAIMED;
        case "donated":
            return DONATED;
        default:
            return ARRIVED;
        }
    }
}
