package seedu.address.diaryfeature.model;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DatePair;
import seedu.address.commons.util.TreeUtil;
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
     * @param target
     * @return
     */
    public DiaryEntry deleteDiaryEntry(int target) {

        return diaryBook.deleteDiaryEntry(target);
    }


    public void setDiaryEntryPrivate(int index) {
        diaryBook.setDiaryEntryPrivate(index);
    }

    public void setDiaryEntryUnPrivate(int index) {
        diaryBook.setDiaryEntryUnPrivate(index);
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


    public XYChart.Series<String, Number> getDiaryChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        //setup resources
        TreeUtil<DatePair> treeUtil = new TreeUtil<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");


        FXCollections.unmodifiableObservableList(filteredDiaryBook).stream().forEach(diaryEntry -> {
            Date date = diaryEntry.getDate();
            String displayDate = dateFormatter.format(date);

            DatePair defaultIfMissing = new DatePair(0, date);
            Function<DatePair, DatePair> incrementFunction =
                    datePair -> new DatePair(datePair.getKey() + 1, datePair.getValue());

            treeUtil.add(displayDate, defaultIfMissing, incrementFunction);
        });

        series.getData().addAll(treeUtil.ascendingStream()
                .map(datePair ->
                        new XYChart.Data<String, Number> (
                                dateFormatter.format(datePair.getValue()), datePair.getKey()))
                .collect(Collectors.toList()));
        return series;
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
