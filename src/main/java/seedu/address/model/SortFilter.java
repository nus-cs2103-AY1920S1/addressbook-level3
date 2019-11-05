package seedu.address.model;

/**
 * Gives the specific filter to sort the lists by
 */
public class SortFilter {

    private static int index;

    private String filter;

    public SortFilter(String string, int filterIndex) {
        filter = string;
        index = filterIndex;
    }

    /**
     * Gets the index of the specific filter
     */
    public int getIndex() {
        return index;
    }

}
