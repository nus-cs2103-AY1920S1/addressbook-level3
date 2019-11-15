package dukecooks.model.profile.person.exceptions;

/**
 * Signals that the operation will result in duplicate Profiles (Persons are considered duplicates as long as more
 * than one profile exists).
 */
public class DuplicateProfileException extends RuntimeException {
    public DuplicateProfileException() {
        super("Operation would result in having more than one profile");
    }
}
