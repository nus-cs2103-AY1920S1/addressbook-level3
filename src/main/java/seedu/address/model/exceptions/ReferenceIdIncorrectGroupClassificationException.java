//@@author SakuraBlossom
package seedu.address.model.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Signals that the operation had looked up a {@code ReferenceId} under the wrong classification.
 * (A ReferenceId must strict either belong to a staff member or patient).
 */
public class ReferenceIdIncorrectGroupClassificationException extends ParseException {
    public final String id;

    public ReferenceIdIncorrectGroupClassificationException(
            String id, String alreadyClassifiedAs) {
        super(String.format("'%1$s' was assigned as a %2$s.", id, alreadyClassifiedAs));
        this.id = id;
    }

    public ReferenceIdIncorrectGroupClassificationException(String id, boolean isStaff) {
        this(id, (isStaff ? "staff member" : "patient"));
    }
}
