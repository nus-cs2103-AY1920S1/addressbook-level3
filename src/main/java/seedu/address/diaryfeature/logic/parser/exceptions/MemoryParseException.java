package seedu.address.diaryfeature.logic.parser.exceptions;

    public class MemoryParseException extends DiaryEntryParseException  {
        private final String ERROR_MESSAGE =
                "In particular, something is wrong with the memory! Either don't put in the m/ prefix\n" +
                        "or put in a memory which is not empty!!!";
        @Override
        public String toString() {
            return getUsage() + ERROR_MESSAGE;
        }

    }
