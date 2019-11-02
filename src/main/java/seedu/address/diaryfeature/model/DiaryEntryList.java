package seedu.address.diaryfeature.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.util.SampleDataUtil;

/**
 *
 */

public class DiaryEntryList {

    private final ObservableList<DiaryEntry> entries = FXCollections.observableArrayList();
    private final ObservableList<DiaryEntry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(entries);

    /**
     *
     * @param entry
     * @return
     */

    public DiaryEntry addDiaryEntry(DiaryEntry entry) {
        entries.add(entry);
        return entry;
    }

    /**
     *
     * @param index
     * @return
     */

    public DiaryEntry deleteDiaryEntry(int index) {
        DiaryEntry removed = entries.remove(index - 1);
        return removed;
    }

    public void setDiaryEntryPrivate(int index) {
        DiaryEntry removed = entries.get(index - 1 );
        removed.setPrivate();
    }

    public void setDiaryEntryUnPrivate(int index) {
        DiaryEntry removed = entries.get(index - 1);
        removed.unPrivate();
    }


    /**
     *
     * @return
     */

    public int getSize() {
        return entries.size();
    }

    /**
     *
     * @return
     */

    public boolean isEmpty() {
        if (entries.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getEntriesAsString() {
        StringBuilder curr = new StringBuilder();
        int i = 0;
        for(DiaryEntry temp: entries) {
            curr.append("Diary Entry: " + i + "\n");
            curr.append(temp.toString());
            curr.append("\n");
        }
        return curr.toString();
    }

    /**
     *
     */

    public void loadSampleData() {
        for (DiaryEntry curr: SampleDataUtil.getSampleDiaryEntry()) {
            entries.add(curr);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DiaryEntry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     *
     * @param someEntry
     * @return
     */

    public boolean contains(DiaryEntry someEntry) {
        return entries.contains(someEntry);
    }
}
