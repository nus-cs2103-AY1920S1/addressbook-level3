package seedu.address.commons.core;

//@@author {lawncegoh}

/**
 * Gives the specific view that the user inputs
 */
public class View {

    private static int index;

    private String togo;

    /**
     * Constructs a view with the specific type and index
     * @param string
     * @param viewIndex tagged to the string, just tells the app what view the user chose
     */
    public View(String string, int viewIndex) {
        togo = string;
        index = viewIndex;
    }

    /**
     * Gets the index of the specific view that should be in place
     * @return
     */
    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof View // instanceof handles nulls
                && index == ((View) other).index // state check
                && togo == ((View) other).togo);
    }

}
