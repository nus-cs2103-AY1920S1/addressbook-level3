package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * MemoryException arises is there are any errors in the memory format
 */
public class MemoryParseException extends DiaryEntryParseException {
    private final String ERROR_MESSAGE =
            "In particular (if you put in the m/ prefix):\n" +
                    "1) Memory can't be empty. \n" +
                    "2) Memory can't be too long (maximum of 100 characters).";

    /**
     * @return String representation of the Error message
     */

    @Override
    public String toString() {
        return getUsage() + ERROR_MESSAGE;
    }

}
