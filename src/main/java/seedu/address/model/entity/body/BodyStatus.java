package seedu.address.model.entity.body;

//@@author ambervoong

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Enumerates all the possible states a body undergoes in the mortuary.
 */
public enum BodyStatus {
    ARRIVED, PENDING_IDENTIFICATION, PENDING_CLAIM, CLAIMED, DONATED, CONTACT_POLICE, PENDING_POLICE_REPORT,
    PENDING_CORONER_APPROVAL;

    /**
     * Parses {@code String status} to return the corresponding {@code Status}.
     */
    public static BodyStatus parseBodyStatus(String status) throws ParseException {
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
        case "contact police":
            return CONTACT_POLICE;
        case "pending police report":
            return PENDING_POLICE_REPORT;
        case "claimed":
            return CLAIMED;
        case "donated":
            return DONATED;
        default:
            throw new ParseException("Invalid Body Status: " + statusLowerCaps);
        }
    }
}
