package seedu.address.DiaryFeature.DiaryMain;

import java.util.LinkedList;

public class DiaryList {
    private LinkedList<DiaryObject> myContainer;

    public DiaryList() {
        myContainer = new LinkedList();
    }

    public DiaryObject addDiaryEntry(DiaryObject x) {
        myContainer.add(x);
        return x;
    }

    public DiaryObject removeDiaryEntry(int x) {
        DiaryObject deleted = myContainer.remove(x-1);
        return deleted;
    }

}
