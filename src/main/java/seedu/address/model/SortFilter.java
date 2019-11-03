package seedu.address.model;

import seedu.address.logic.parser.ParserUtil;

/**
 * Gives the specific filter to sort the lists by
 */
public class SortFilter {

    private static int index;

    private String filter;

    public SortFilter(String string, int filterIndex) {
        if (!(ParserUtil.checkFilter(string))) {
            throw new IllegalArgumentException();
        }
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
