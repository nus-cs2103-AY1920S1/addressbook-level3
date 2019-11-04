package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

public class DiaryEntryParseException extends Exception {
    private final String USAGE_MESSAGE =
            "Wrong format for the add diary entry command! \n" +
                    "Add Diary Entries like this (place and memory are optional): add t/{title} d/{date} p/{place} m/{memory}";

    /**
     * DiaryEntryParseException serves as the template exception for other entry exceptions
     */
    public DiaryEntryParseException() {
        super();
    }

    /**
     *
     * @return String representation of the Error message
     */

    public String toString() {
        return USAGE_MESSAGE;
    }

    /**
     *
     * @return String representation of the Error message
     */
    public String getUsage() {
        return USAGE_MESSAGE + "\n";
    }

}
