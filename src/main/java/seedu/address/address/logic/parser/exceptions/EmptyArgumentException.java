package seedu.address.address.logic.parser.exceptions;

import seedu.address.logic.parser.Parser;

public class EmptyArgumentException extends Exception {
    private String parserName;
    private String filler = "Cant have the arguments for the ";
    private String second = "empty man!!!";

    public EmptyArgumentException(String input) {
        parserName = input;
    }
    public String toString() {
        return filler + parserName + second;
    }
}
