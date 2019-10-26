package seedu.address.diaryfeature.model.diaryEntry.exceptions;

public class TitleException extends Exception {
    final String ERROR_MESSAGE = "Title can't be empty bub! Have a proper title, make your memories recognisable!!!";

    public TitleException() {
        super();
    }

    @Override
    public String toString() {
        return ERROR_MESSAGE;
    }

}
