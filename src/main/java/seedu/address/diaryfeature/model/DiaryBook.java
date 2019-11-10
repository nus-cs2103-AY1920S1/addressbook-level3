package seedu.address.diaryfeature.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class DiaryBook {

    private final DiaryEntryList entries;
    private Optional<Details> details;


    public DiaryBook() {
        entries = new DiaryEntryList();
        details = Optional.empty();
    }

    public DiaryBook loadData(DiaryEntry[] myEntries) {
        for (DiaryEntry curr : myEntries) {
            entries.addDiaryEntry(curr);
        }
        return this;
    }

    public void setDetails(Details attempt) {
        if (details.isEmpty()) {
        details = Optional.of(attempt);
    }

}

    public boolean hasPassword() {
        return (details.isPresent());
    }

    public void setinnerDetails(Optional<Details> input) {
        if(input.isEmpty()) {
            details = Optional.empty();
        } else {
            details = input;
        }
    }


    public boolean checkDetails(Details input) {
        if(details.isEmpty()) {
            return false;
        } else {
            return details.get().checkDetails(input);
        }
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public DiaryEntry addDiaryEntry(DiaryEntry p) {

        return entries.addDiaryEntry(p);
    }
    public Optional<Details> getDetails() {
        return details;
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public DiaryEntry deleteDiaryEntry(DiaryEntry input) {

        return entries.deleteDiaryEntry(input);
    }

    public void setDiaryEntryPrivate(int index) {
         entries.setDiaryEntryPrivate(index);
    }

    public void setDiaryEntryUnPrivate(int index) {
       entries.setDiaryEntryUnPrivate(index);
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

    public boolean contains(DiaryEntry otherEntry) {
        return entries.contains(otherEntry);
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
