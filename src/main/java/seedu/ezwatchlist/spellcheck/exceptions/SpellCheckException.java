package seedu.ezwatchlist.spellcheck.exceptions;

/**
 * Represents an error when spell check fails to suggest a spelling change.
 */
public class SpellCheckException extends Exception {

    public SpellCheckException(String message) {
        super(message);
    }

}

