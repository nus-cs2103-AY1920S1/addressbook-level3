package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * MemoryException arises is there are any errors in the memory format
 */
public class TitleParseException extends DiaryEntryParseException {
    private final String ERROR_MESSAGE =
            "In particular:\n" +
                    "1) Title can't be empty. \n" +
                    "2) Title can't be too long (maximum of 50 characters).";

    /**
     * @return String representation of the Error message
     */
    @Override
    public String toString() {
        return getUsage() + ERROR_MESSAGE;
    }


}
