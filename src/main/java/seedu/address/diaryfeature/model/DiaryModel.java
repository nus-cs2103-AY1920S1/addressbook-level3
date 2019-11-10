package seedu.address.diaryfeature.model;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.MonthData;
import seedu.address.commons.util.StatisticsUtil;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

/**
 * Represents the in-memory model of the address book data.
 */
public class DiaryModel {
    private static final Logger logger = LogsCenter.getLogger(DiaryModel.class);
    public static final Predicate<DiaryEntry> PREDICATE_SHOW_ALL_EVENTS = unused -> true;


    private final DiaryBook diaryBook;
    private final FilteredList<DiaryEntry> filteredDiaryBook;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public DiaryModel() {
        logger.fine("Initializing empty diarybook");
        this.diaryBook = new DiaryBook();
        filteredDiaryBook = new FilteredList<>(this.diaryBook.getDiaryEntryList());
    }

    public DiaryModel(DiaryBook input) {
        logger.fine("Initializing diarybook from stored var");
        this.diaryBook = input;
        filteredDiaryBook = new FilteredList<>(input.getDiaryEntryList());
    }

    /**
     *
     * @param input
     * @return
     */
    public DiaryEntry deleteDiaryEntry(DiaryEntry input) {

        return diaryBook.deleteDiaryEntry(input);
    }


    public void setDiaryEntryPrivate(DiaryEntry input) {
        diaryBook.setDiaryEntryPrivate(input);
    }

    public void setDiaryEntryUnPrivate(DiaryEntry input) {
        diaryBook.setDiaryEntryUnPrivate(input);
    }

    public void setDetails(Details attempt) {
        diaryBook.setDetails(attempt);
    }
    public String getEntriesAsString() {
        return diaryBook.getEntriesAsString();
    }

    /**
     *
     * @param diaryEntry
     * @return
     */

    public DiaryEntry addDiaryEntry (DiaryEntry diaryEntry) {

        return diaryBook.addDiaryEntry(diaryEntry);
    }

    public boolean contains(DiaryEntry otherEntry) {
        return diaryBook.contains(otherEntry);
    }

    public Optional<Details> getDetails() {
        return diaryBook.getDetails();
    }

    public boolean checkDetails(Details input) {
        return diaryBook.checkDetails(input);
    }
    public boolean hasPassword() {
        return diaryBook.hasPassword();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    public ObservableList<DiaryEntry> getFilteredDiaryEntryList() {
        return filteredDiaryBook;
    }


    public void updateFilteredDiaryList(Predicate<DiaryEntry> predicate) {
        requireNonNull(predicate);
        filteredDiaryBook.setPredicate(predicate);
    }

    public void setinnerDetails(Optional<Details> input) {
       diaryBook.setinnerDetails(input);
    }

    public DiaryBook getDiaryBook() {
        return  this.diaryBook;
    }

    //=========== Statistics =================================================================================

    public int getTotalDiaryEntries() {
        return filteredDiaryBook.size();
    }


    public XYChart.Series<String, Number> getDiaryBarChart() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-yyyy");
        Function<DiaryEntry, MonthData> toMonthDataFunction =
                diaryEntry -> new MonthData(0, dateFormatter.format(diaryEntry.getDate()));
        return StatisticsUtil.getMonthDataSeries(filteredDiaryBook, toMonthDataFunction);
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
