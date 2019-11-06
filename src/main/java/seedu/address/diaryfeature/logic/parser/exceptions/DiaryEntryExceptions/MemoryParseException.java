package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * MemoryException arises is there are any errors in the memory format
 */
public class MemoryParseException extends DiaryEntryParseException {
        private final String ERROR_MESSAGE =
                "In particular, something is wrong with the memory.\nEither don't put in the m/ prefix\n" +
                        "Or, put in a memory which is not empty.";

    /**
     *
     * @return String representation of the Error message
     */

    @Override
        public String toString() {
            return getUsage() + ERROR_MESSAGE;
        }

    }
