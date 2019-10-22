package seedu.address.model.cheatsheet;

import java.util.Comparator;

/**
 * Sorts the indexes of the contents by ascending order
 */
public class ContentSortByIndex implements Comparator<Content> {

    /**
     * Compares the indexes and sorts them
     * @param c1 index of content
     * @param c2 index of another content
     * @return indicates which comes first in order
     */
    public int compare(Content c1, Content c2) {
        int index1 = c1.getIndex();
        int index2 = c2.getIndex();

        if (index1 < index2) {
            return -1;
        } else if (index1 == index2) {
            return 0;
        } else {
            return 1;
        }
    }
}
