package seedu.address.model.statistics;

import java.time.LocalDate;
import java.time.Month;

import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Entry;


/**
 * Handles calculation of statistics.
 */
public class StatisticsManager {
    private double lastMonthExpenses;
    private ObservableMap<Integer, MonthList> monthlyRecord;
    private DailyList currentDailyList;

    /**
     * Manages the general Statistics.
     */
    public StatisticsManager(FilteredList<Entry> filteredList) {
        int currentMonth = LocalDate.now().getMonth().getValue();
        int currentDay = LocalDate.now().getDayOfMonth();
        int currentYear = LocalDate.now().getYear();
        initRecords(filteredList, currentMonth, currentYear);

    }

    /**
     * Loads the Records from scratch.
     */
    private void initRecords(FilteredList<Entry> filteredList, int month, int currentYear) {
        for (int i = 1; i <= month; i++) {
            FilteredList<Entry> filteredByMonth = filteredList;
            filteredByMonth.setPredicate(new EntryTimeContainsPredicate(i));
            MonthList monthToCompare = new MonthList(filteredByMonth, Month.of(month), currentYear);
            monthlyRecord.put(i, monthToCompare);
        }
    }

    public void getTagsForTimePeriod() {

    }

}
