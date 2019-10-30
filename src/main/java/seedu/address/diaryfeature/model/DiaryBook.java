package seedu.address.diaryfeature.model;

import javafx.collections.ObservableList;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class DiaryBook {

    private final DiaryEntryList entries;


    public DiaryBook() {
        entries = new DiaryEntryList();
       // if (entries.isEmpty()) {
        //    entries.loadSampleData();
      //  }
        //This is a current, temp version
        //to ensure that my diary list has something
        //when we start
    }

    public DiaryBook loadData(DiaryEntry[] myEntries) {
        for(DiaryEntry curr: myEntries) {
            entries.addDiaryEntry(curr);
        }
        return this;
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public DiaryEntry addDiaryEntry(DiaryEntry p) {

        return entries.addDiaryEntry(p);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public DiaryEntry deleteDiaryEntry(int x) {

        return entries.deleteDiaryEntry(x);
    }

    public String getEntriesAsString() {
        return entries.getEntriesAsString();
    }

    //// util methods

    @Override
    public String toString() {
        return entries.asUnmodifiableObservableList().size() + " entries";
    }

    public ObservableList<DiaryEntry> getDiaryEntryList() {
        return entries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryBook // instanceof handles nulls
                && entries.equals(((DiaryBook) other).entries));
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }
}
