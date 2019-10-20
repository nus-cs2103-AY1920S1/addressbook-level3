package seedu.address.ui.exceptions;

import seedu.address.ui.ResultViewType;

/**
 * Represents an error when a result view type that is not found in the {@code ResultViewType} is used.
 */
public class InvalidResultViewTypeException extends EnumConstantNotPresentException {

    public InvalidResultViewTypeException(String constantName) {
        super(ResultViewType.class, constantName);
    }

}
