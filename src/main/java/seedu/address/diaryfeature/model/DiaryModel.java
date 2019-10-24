package seedu.address.diaryfeature.model;


import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

/**
 * Represents the in-memory model of the address book data.
 */
public class DiaryModel {
    private static final Logger logger = LogsCenter.getLogger(DiaryModel.class);

    private final DiaryBook diaryBook;
    private final FilteredList<DiaryEntry> filteredDiaryBook;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public DiaryModel() {
        logger.fine("Initializing diarybook");
        this.diaryBook = new DiaryBook();
        filteredDiaryBook = new FilteredList<>(this.diaryBook.getDiaryEntryList());
    }


    public void deleteDiaryEntry(int target) {
        diaryBook.deleteDiaryEntry(target);
    }

    public void addDiaryEntry(DiaryEntry diaryEntry) {
        diaryBook.addDiaryEntry(diaryEntry);
    }



    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    public ObservableList<DiaryEntry> getFilteredDiaryEntryList() {
        return filteredDiaryBook;
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DiaryModel)) {
            return false;
        }

        // state check
        DiaryModel other = (DiaryModel) obj;
        return diaryBook.equals(other.diaryBook)
                && filteredDiaryBook.equals(other.filteredDiaryBook);
    }

}
