package seedu.address.model.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;

public class ReferenceIdIncorrectGroupClassificationException extends ParseException {
    public ReferenceIdIncorrectGroupClassificationException(
            ReferenceId id, String alreadyClassifiedAs, String currentClassification) {
        super(String.format("%1$s was already assigned as a %2$s and cannot be re-assigned as a %3$s",
                id, alreadyClassifiedAs, currentClassification));
    }

    public ReferenceIdIncorrectGroupClassificationException(ReferenceId id) {
        this(id, (id.isStaffDoctor() ? "staff member" : "patient"),
                (!id.isStaffDoctor() ? "staff member" : "patient"));
    }
}
