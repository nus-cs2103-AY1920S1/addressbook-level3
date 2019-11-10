//@@author SakuraBlossom
package seedu.address.model.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Signals that the operation will result in the re-classification of {@code ReferenceId}.
 * (A ReferenceId must strict either belong to a staff member or patient).
 */
public class ReferenceIdCannotChangeClassificationException extends ParseException {
    public ReferenceIdCannotChangeClassificationException(
            String id, String alreadyClassifiedAs, String currentClassification) {
        super(String.format("'%1$s' was already assigned as a %2$s and cannot be re-assigned as a %3$s.",
                id, alreadyClassifiedAs, currentClassification));
    }

    public ReferenceIdCannotChangeClassificationException(String id, boolean isStaff) {
        this(id, (isStaff ? "staff member" : "patient"), (!isStaff ? "staff member" : "patient"));
    }
}
