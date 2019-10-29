package seedu.address.commons.exceptions;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Alias;

/**
 * Represents an error which occurs when trying to add an Alias which has an CommandWord which
 * refers to another Alias in the AliasMappings.
 */
public class RecursiveAliasException extends Exception {
    public RecursiveAliasException(Alias cause) {
        super(String.format("The alias \"%s\" cannot be added because it may cause recursion.", cause.getAliasName()));
        requireNonNull(cause);
    }

}
