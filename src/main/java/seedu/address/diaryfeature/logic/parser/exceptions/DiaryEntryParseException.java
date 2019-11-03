package seedu.address.diaryfeature.logic.parser.exceptions;

public class DiaryEntryParseException extends Exception {
    private final String USAGE_MESSAGE =
            "Wrong format for the add diary entry command! \n" +
                    "Add Diary Entries like this (place and memory are optional): add t/title d/date p/place m/memory";
    /**
     *
     */
    public DiaryEntryParseException() {
        super();
    }

    /**
     *
     * @return
     */

    public String toString() {
        return USAGE_MESSAGE;
    }
    public String getUsage() {
        return USAGE_MESSAGE + "\n";
    }

}
