package seedu.address.model;

/**
 * Gives the specific view that the user inputs
 */
public class View {

    private final String togo;

    public static int index;

    /**
     * Constructs a view with the specific type and index
     * @param string
     * @param viewIndex tagged to the string, just tells the app what view the user chose
     */
    public View(String string, Integer viewIndex) {
        togo = string;
        index = viewIndex;
    }

    /**
     * Gets the index of the specific view that should be in place
     * @return
     */
    public Integer getIndex() {
        return index;
    }
}
