package seedu.address.diaryfeature.logic.parser.exceptions;


public class DateParseException extends DiaryEntryParseException {
    private final String ERROR_MESSAGE =
            "In particular, something is wrong with your date and time! Enter it like this:\n" +
                    "dd/mm/yyyy HHmm  EG: d/31/12/2019 2359 .";
    @Override
    public String toString() {
        return getUsage() + ERROR_MESSAGE;
    }




}
