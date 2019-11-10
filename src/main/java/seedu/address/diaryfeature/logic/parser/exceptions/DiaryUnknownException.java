package seedu.address.diaryfeature.logic.parser.exceptions;


import seedu.address.logic.parser.exceptions.ParseException;

/**
 * DiaryUnknownException arises if the command is unknown
 */
public class DiaryUnknownException extends ParseException {
    private static final String UNKNOWN_COMMAND_MESSAGE = "Hey! Sorry, but Diary does not have that command yet :(\n" +
            "Try typing help to see the available commands";

    public DiaryUnknownException() {
        super(UNKNOWN_COMMAND_MESSAGE);
    }

    /**
     * @return String representation of the Error message
     */
    public String toString() {
        return UNKNOWN_COMMAND_MESSAGE;
    }
}
