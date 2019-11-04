package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * MemoryException arises is there are any errors in the memory format
 */
public class TitleParseException extends DiaryEntryParseException {
    private final String ERROR_MESSAGE =
            "In particular, title can't be empty :) Have a proper title, make your memories recognisable!!!";

    /**
     *
     * @return String representation of the Error message
     */
    @Override
    public String toString() {
        return getUsage() + ERROR_MESSAGE;
    }


}
