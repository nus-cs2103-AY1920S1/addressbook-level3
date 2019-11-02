package seedu.address.diaryfeature.logic.parser.exceptions;

public class PlaceParseException extends DiaryEntryParseException  {
        private final String ERROR_MESSAGE =
        "In particular, something is wrong with the place! Either don't put in the p/ prefix\n" +
                "or put in a place which is not empty!!!";

        @Override
        public String toString() {
                return getUsage() + ERROR_MESSAGE;
        }

}
