package seedu.address.model;

import seedu.address.commons.exceptions.ViewException;

/**
 * Gives the specific view that the user inputs
 */
public class View {

    private static int index;

    private static final String CORRECT_VIEW_CONTACTS = "contacts";

    private static final String CORRECT_VIEW_CLAIMS = "claims";

    private static final String CORRECT_VIEW_INCOMES = "incomes";

    private String togo;

    /**
     * Constructs a view with the specific type and index
     * @param string
     * @param viewIndex tagged to the string, just tells the app what view the user chose
     */
    public View(String string, Integer viewIndex) throws ViewException {
        if ((string.equals(CORRECT_VIEW_CONTACTS) && viewIndex == 1)
                || (string.equals(CORRECT_VIEW_CLAIMS) && viewIndex == 2)
                || (string.equals(CORRECT_VIEW_INCOMES)) && viewIndex == 3) {
            togo = string;
            index = viewIndex;
        } else {
            throw new ViewException("View is not accepted");
        }

    }

    /**
     * Gets the index of the specific view that should be in place
     * @return
     */
    public Integer getIndex() {
        return index;
    }
}
