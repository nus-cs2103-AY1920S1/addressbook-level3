package seedu.address.diaryfeature.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.util.SampleDataUtil;

public class DiaryEntryList {

    private final ObservableList<DiaryEntry> entries = FXCollections.observableArrayList();
    private final ObservableList<DiaryEntry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(entries);

    public void addDiaryEntry(DiaryEntry entry) {
        entries.add(entry);
    }

    public void deleteDiaryEntry(int index) {
        entries.remove(index - 1);
    }

    public int getSize() {
        return entries.size();
    }

    public boolean isEmpty() {
        if(entries.size() ==0) {
            return true;
        } else {
            return false;
        }
    }

    public void loadSampleData() {
        for(DiaryEntry curr: SampleDataUtil.getSampleDiaryEntry()) {
            entries.add(curr);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DiaryEntry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    public boolean contains(DiaryEntry someEntry) {
        return entries.contains(someEntry);
    }


}