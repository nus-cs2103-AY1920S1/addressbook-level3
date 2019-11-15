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

    /**
     * Construct a new DiaryBook
     */
    public DiaryBook() {
        entries = new DiaryEntryList();
        details = Optional.empty();
    }

    /**
     * Load sample entries
     * @param myEntries
     * @return DiaryBook
     */
    public DiaryBook loadData(DiaryEntry[] myEntries) {
        entries.loadData(myEntries);
        return this;
    }

    /**
     *Sets a new set of details
     * @param attempt
     */
    public void setDetails(Details attempt) {
        if (details.isEmpty()) {
            details = Optional.of(attempt);
        }

    }

    /**
     *
     * @return
     */
    public boolean hasDetails() {
        return (details.isPresent());
    }

    /**
     *
     * @param input
     */
    public void setinnerDetails(Optional<Details> input) {
        if (input.isEmpty()) {
            details = Optional.empty();
        } else {
            details = input;
        }
    }

    /**
     *
     * @param input
     * @return
     */

    public boolean checkDetails(Details input) {
        if (details.isEmpty()) {
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

    public void setDiaryEntryPrivate(DiaryEntry input) {
        entries.setDiaryEntryPrivate(input);
    }

    public void setDiaryEntryUnPrivate(DiaryEntry input) {
        entries.setDiaryEntryUnPrivate(input);
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
