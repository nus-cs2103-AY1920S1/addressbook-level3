package seedu.address.model.category.exceptions;
/**
 * Signals that the operation will result in
 * duplicate categories (categories are considered duplicates
 * if they have the same value).
 */
public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException() {
        super("Operation would result in duplicate categories");
    }
}

