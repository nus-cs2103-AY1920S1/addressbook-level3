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
     * Add a new diary entry
     * @param entry
     * @return DiaryEntry
     */
    public DiaryEntry addDiaryEntry(DiaryEntry entry) {
        entries.add(entry);
        return entry;
    }

    /**
     * Delete the indicated diary entry
     * @param input
     * @return DiaryEntry
     */
    public DiaryEntry deleteDiaryEntry(DiaryEntry input) {
        if (entries.remove(input)) {
            return input.copy();
        }
        assert input != null;
        System.out.println("Entry should not be null and should exist in the list");
        return null;
    }
    /**
     * Set the indicated diary as private
     * @param output
     */
    public void setDiaryEntryPrivate(DiaryEntry output) {
        int change = entries.indexOf(output);
        DiaryEntry input = entries.get(change);
        input.setPrivate();
        entries.set(change, input);
    }

    /**
     * Unprivate the indicated diary
     * @param output
     */
    public void setDiaryEntryUnPrivate(DiaryEntry output) {
        int change = entries.indexOf(output);
        DiaryEntry input = entries.get(change);
        input.unPrivate();
        entries.set(change, input);
    }

    /**
     * Get the size of the diary list
     * @return int
     */

    public int getSize() {
        return entries.size();
    }

    /**
     * Check if the Diary List is empty
     * @return boolean
     */

    public boolean isEmpty() {
        if (entries.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get all entries as a string
     * @return String
     */

    public String getEntriesAsString() {
        StringBuilder curr = new StringBuilder();
        int i = 0;
        for (DiaryEntry temp : entries) {
            curr.append("Diary Entry: " + i + "\n");
            curr.append(temp.toString());
            curr.append("\n");
        }
        return curr.toString();
    }

    /**
     * In case of a corrupted/empty file, load sample data into DiaryBook
     */

    public void loadSampleData() {
        for (DiaryEntry curr : SampleDataUtil.getSampleDiaryEntry()) {
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
