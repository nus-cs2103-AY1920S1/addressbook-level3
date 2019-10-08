package seedu.address.model.competition.exceptions;

/**
 * Signals that the operation will result in duplicate Competitions.
 * Competitions are considered duplicates if they have the same identity.
 */
public class DuplicateCompetitionException extends RuntimeException {
    public DuplicateCompetitionException() {
        super("Operation would result in duplicate competitions");
    }
}
