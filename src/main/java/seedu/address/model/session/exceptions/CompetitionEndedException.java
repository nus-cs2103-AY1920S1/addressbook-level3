package seedu.address.model.session.exceptions;

import seedu.address.model.competition.Competition;

/**
 * Handles the case where the the last attempt has been made, and the competition has ended.
 */
public class CompetitionEndedException extends RuntimeException {

    public CompetitionEndedException(Competition competition) {
        super(competition + " has now come to an end. Thank you.");
    }
}
