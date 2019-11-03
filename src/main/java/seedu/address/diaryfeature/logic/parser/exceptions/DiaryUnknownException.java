package seedu.address.diaryfeature.logic.parser.exceptions;

/**
 * DiaryUnknownException arises if the command is unknown
 */
public class DiaryUnknownException extends Exception {
    private final String UNKNOWN_COMMAND_MESSAGE = "Hey!, Sorry, but Diary does not have that command yet :(\n" +
            "Try typing help to see the available commands";

    /**
     *
     * @return String representation of the Error message
     */
    public String toString() {
        return UNKNOWN_COMMAND_MESSAGE;
    }
}
