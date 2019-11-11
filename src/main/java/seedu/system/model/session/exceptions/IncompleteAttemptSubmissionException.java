package seedu.system.model.session.exceptions;

import java.util.List;

import seedu.system.model.person.Name;

/**
 * Handles the case where the user tries to prepare for the session when not all participants
 * for the competition have submitted their attempts.
 */
public class IncompleteAttemptSubmissionException extends RuntimeException {
    private static final String message = "have not submitted their attempts."
            + " Try again after all athletes have done so.";
    private final List<Name> namesOfNonSubmission;

    public IncompleteAttemptSubmissionException(List<Name> nameList) {
        this.namesOfNonSubmission = nameList;
    }

    @Override
    public String getMessage() {
        StringBuilder nameString = new StringBuilder();
        for (Name name : namesOfNonSubmission) {
            nameString.append(name).append(", ");
        }
        return nameString.toString() + message;
    }
}
