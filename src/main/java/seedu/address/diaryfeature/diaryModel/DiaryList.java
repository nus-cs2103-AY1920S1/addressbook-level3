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
        myContainer.add(new DiaryObject("Placemat","12.12.2019","NUS Comp 2","CsPrayers","Coding challenge"));
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

    public String getEntriesAsString() {
        String diaryEntries = "";
        for(DiaryObject curr: myContainer) {
            diaryEntries += (curr + "\n");
        }
        return diaryEntries;
    }


}
