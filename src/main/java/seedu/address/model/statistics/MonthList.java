package seedu.address.model.statistics;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.util.EntryComparator;

/**
 * Contains the entries for the current Month in the idea.
 */
public class MonthList {

    private ObservableMap<Integer, DailyList> dailyRecord;
    private FilteredList<Entry> filteredListForMonth;
    private SortType sortByTime = new SortType("Time");
    private SortSequence sortByDesc = new SortSequence("Descending");
    private Month month;
    private int year;
    private double monthExpenseTotal;

    /**
     * Contains the FilteredList of entries for the month.
     */
    public MonthList(FilteredList<Entry> filteredList, Month month, int year) {
        this.filteredListForMonth = filteredList;
        this.month = month;
        this.year = year;
        initRecords();
    }

    private void initRecords() {
        filteredListForMonth.sort(new EntryComparator(sortByTime, sortByDesc));
        createObsMap();
    }

    /**
     * Creates an ObservableMap of Dailylists
     */
    private void createObsMap() {
        for (int i = 0; i < 31; i++) {
            try {
                LocalDate.of(year, month.getValue(), i);
            } catch (DateTimeException e) {
                continue;
            }
            FilteredList<Entry> filteredListByDay = filteredListForMonth.filtered(new EntryContainsDayPredicate(i));
            DailyList dailyList = new DailyList(filteredListByDay, LocalDate.of(year, month.getValue(), i));
            monthExpenseTotal = monthExpenseTotal + dailyList.getTotalExpense();
            dailyRecord.put(i, dailyList);
        }
    }



}
