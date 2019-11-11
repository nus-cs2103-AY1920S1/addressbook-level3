package seedu.system.model.session.exceptions;

import seedu.system.model.person.Gender;
import seedu.system.model.person.Person;

/**
 * Handles the case where an athlete has already submitted his attempts, and is trying to submit again.
 */
public class AttemptsSubmittedException extends RuntimeException {
    private final String message;

    public AttemptsSubmittedException(Person participant) {
        this.message = String.format("%s has already submitted %s attempts.",
                participant.getName(), getHisOrHer(participant.getGender()));
    }

    private String getHisOrHer(Gender gender) {
        // we use if else because there are only 2 valid types of gender
        if (gender.equals(Gender.MALE)) {
            return "his";
        } else {
            return "her";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
