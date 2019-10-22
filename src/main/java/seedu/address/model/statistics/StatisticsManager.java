package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;

public class StatisticsManager {
    private double lastMonthExpenses;
    private ObservableMap<Integer, MonthList> monthlyRecord;
    private DailyList currentDailyList;

    public StatisticsManager(FilteredList<Entry> filteredList) {
        int currentMonth = LocalDate.now().getMonth().getValue();
        int currentDay = LocalDate.now().getDayOfMonth();
        int currentYear = LocalDate.now().getYear();
        initRecords(filteredList, currentMonth, currentYear);

    }

    private void initRecords(FilteredList<Entry> filteredList, int month, int currentYear) {
        for (int i = 1; i <= month; i++) {
            FilteredList<Expense> filteredByMonth = filteredList.setPredicate(new EntryTimeContainsPredicate(i));
            MonthList monthToCompare = new MonthList(filteredByMonth, Month.of(month), currentYear);
            monthlyRecord.put(i, monthToCompare);
        }
    }

    public void getTagsForTimePeriod() {

    }

}
