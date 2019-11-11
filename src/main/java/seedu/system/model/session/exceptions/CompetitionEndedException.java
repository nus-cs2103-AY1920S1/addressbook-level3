package seedu.system.model.session.exceptions;

import seedu.system.model.competition.Competition;

/**
 * Handles the case where the the last attempt has been made, and the competition has ended.
 */
public class CompetitionEndedException extends RuntimeException {

    public CompetitionEndedException(Competition competition) {
        super(competition + " has now come to an end.\n"
                + "Enter 'rank' or 'ranklist' to get the ranks of the participation/competition.\n"
                + "Enter 'endSession' to end the competition session.");
    }
}
