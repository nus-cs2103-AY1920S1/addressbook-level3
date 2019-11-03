package seedu.address.diaryfeature.logic.parser.exceptions;

public class DiaryUnknownException extends Exception {
    private final String UNKNOWN_COMMAND_MESSAGE = "Hey!, Sorry, but Diary does not have that command yet :(\n" +
            "Try typing help to see the available commands";
    public String toString() {
        return UNKNOWN_COMMAND_MESSAGE;
    }
}
