package calofit.model.dish.exceptions;

/**
 * Signals that the operation will result in duplicate Dishes (Dishes are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDishException extends RuntimeException {
    public DuplicateDishException() {
        super("Operation would result in duplicate dishes");
    }
}
