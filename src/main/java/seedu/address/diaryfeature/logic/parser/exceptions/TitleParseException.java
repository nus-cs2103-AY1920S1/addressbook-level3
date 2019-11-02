package seedu.address.diaryfeature.logic.parser.exceptions;

/**
 *
 */
public class TitleParseException extends DiaryEntryParseException {
    private final String ERROR_MESSAGE =
            "In particular, title can't be empty :) Have a proper title, make your memories recognisable!!!";

    @Override
    public String toString() {
        return getUsage() + ERROR_MESSAGE;
    }


}
