package seedu.address.diaryfeature.diaryModel;

import java.util.LinkedList;

/**
 *
 */
public class DiaryList {
    private LinkedList<DiaryObject> myContainer;

    /**
     *
     */
    public DiaryList() {
        myContainer = new LinkedList<>();
    }

    /**
     *
     * @param x
     * @return diaryObject
     */
    public DiaryObject addEntry(DiaryObject x) {
        myContainer.add(x);
        return x;
    }

    /**
     *
     * @param x
     * @return
     */

    public DiaryObject deleteEntry(int x) {
        DiaryObject deleted = myContainer.remove(x-1);
        return deleted;
    }

}
