package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * PlaceException arises is there are any errors in the place format
 */
public class PlaceParseException extends DiaryEntryParseException {
        private final String ERROR_MESSAGE =
                "In particular (if you put in the p/ prefix):\n" +
                        "1) Place can't be empty. \n" +
                        "2) Place can't be too long (maximum of 100 characters).";

        /**
         *
         * @return String representation of the Error message
         */

        @Override
        public String toString() {
                return getUsage() + ERROR_MESSAGE;
        }

}
