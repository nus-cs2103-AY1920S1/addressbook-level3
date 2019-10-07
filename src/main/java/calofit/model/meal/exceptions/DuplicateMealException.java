package calofit.model.meal.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMealException extends RuntimeException {
    public DuplicateMealException() {
        super("Operation would result in duplicate meals");
    }
}
