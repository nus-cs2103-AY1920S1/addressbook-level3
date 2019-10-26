package seedu.address.diaryfeature.model.exceptions;

/**
 *
 */
public class TitleException extends Exception {
    private final String ERROR_MESSAGE =
            "Title can't be empty bub! Have a proper title, make your memories recognisable!!!";

    /**
     *
     */
    public TitleException() {
        super();
    }

    /**
     *
     * @return
     */

    @Override
    public String toString() {
        return ERROR_MESSAGE;
    }

}
