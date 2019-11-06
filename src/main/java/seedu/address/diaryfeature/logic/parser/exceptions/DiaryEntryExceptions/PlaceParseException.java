package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * PlaceException arises is there are any errors in the place format
 */
public class PlaceParseException extends DiaryEntryParseException {
        private final String ERROR_MESSAGE =
        "In particular, something is wrong with the place.\nEither don't put in the p/ prefix\n" +
                "Or put in a place which is not empty.";

        /**
         *
         * @return String representation of the Error message
         */

        @Override
        public String toString() {
                return getUsage() + ERROR_MESSAGE;
        }

}
