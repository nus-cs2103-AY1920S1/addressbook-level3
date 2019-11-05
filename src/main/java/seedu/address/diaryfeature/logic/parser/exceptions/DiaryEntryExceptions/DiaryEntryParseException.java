package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

import seedu.address.logic.parser.exceptions.ParseException;

public class DiaryEntryParseException extends ParseException {
    private static final String USAGE_MESSAGE =
            "Wrong format for the add diary entry command! \n" +
                    "Add Diary Entries like this (place and memory are optional): add t/{title} d/{date} p/{place} m/{memory}";

    /**
     * DiaryEntryParseException serves as the template exception for other entry exceptions
     */
    public DiaryEntryParseException() {
        super(USAGE_MESSAGE);
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
