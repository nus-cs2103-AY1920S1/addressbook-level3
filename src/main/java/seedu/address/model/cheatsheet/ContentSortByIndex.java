package seedu.address.model.cheatsheet;

import java.util.Comparator;

public class ContentSortByIndex implements Comparator<Content> {

    public int compare(Content c1, Content c2)
    {
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
