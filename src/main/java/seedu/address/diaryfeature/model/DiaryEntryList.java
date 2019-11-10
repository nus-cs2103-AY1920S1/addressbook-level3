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
     * @param input
     * @return
     */

    public DiaryEntry deleteDiaryEntry(DiaryEntry input) {
        if(entries.remove(input)) {
            return input;
        }
        return null;
    }


    public void setDiaryEntryPrivate(DiaryEntry output) {
        int change = entries.indexOf(output);
        DiaryEntry input = entries.get(change);
        input.setPrivate();
        entries.set(change,input);
    }

    public void setDiaryEntryUnPrivate(DiaryEntry output) {
        int change = entries.indexOf(output);
        DiaryEntry input = entries.get(change);
        input.unPrivate();
        entries.set(change,input);
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
