package seedu.address.model.statistics;

import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;


/**
 * Handles calculation of statistics.
 */
public class StatisticsManager implements Statistics {
    private ObservableMap<Integer, ObservableMap<Integer, MonthList>> yearlyRecord;
    private ObservableList<CategoryStatistics> listOfStatsForExpense;
    private ObservableList<CategoryStatistics> listOfStatsForIncome;
    private FilteredList<Expense> modelTotalFilteredExpenses;
    private FilteredList<Income> modelTotalFilteredIncomes;
    private CategoryList listOfCategories;

    /**
     * Manages the general Statistics.
     */
    public StatisticsManager(Model modelManager) {
        this.modelTotalFilteredExpenses = new FilteredList(modelManager.getFilteredExpenses());
        this.modelTotalFilteredIncomes = new FilteredList(modelManager.getFilteredIncomes());
        this.listOfCategories = modelManager.getCategoryList();
        int currentYear = LocalDate.now().getYear();
        yearlyRecord = FXCollections.observableHashMap();
        listOfStatsForExpense = FXCollections.observableArrayList();
        listOfStatsForIncome = FXCollections.observableArrayList();
        initRecords(currentYear);
        initStats();
    }

    /**
     * Loads the Records from scratch. Only creates records for the currentYear to increase speed for startup.
     */
    private void initRecords(int currentYear) {
        ObservableMap<Integer, MonthList> monthlyRecord = FXCollections.observableHashMap();
        for (int i = 1; i <= 12; i++) {
            FilteredList<Expense> filteredExpenseByMonth = modelTotalFilteredExpenses;
            FilteredList<Income> filteredIncomeByMonth = modelTotalFilteredIncomes;
            FilteredList<Expense> filteredExpenses = new FilteredList<>(filteredExpenseByMonth,
                    new EntryTimeContainsPredicate(i, currentYear));
            FilteredList<Income> filteredIncome = new FilteredList<>(filteredIncomeByMonth,
                    new EntryTimeContainsPredicate(i, currentYear));
            MonthList monthToCompare = new MonthList(listOfCategories, filteredExpenses, filteredIncome,
                    Month.of(i), currentYear);
            monthlyRecord.put(i, monthToCompare);
        }
        yearlyRecord.put(currentYear, monthlyRecord);
    }

    /**
     * Initiates the Statistics for the listsOfCategories, and adds Listeners for the relevant ObservableLists.
     */
    private void initStats() {
        listOfCategories.getInternalListForOtherEntries().stream().forEach(t -> listOfStatsForExpense
                .add(new CategoryStatistics(t, 0.00)));
        listOfCategories.getInternalListForIncome().stream().forEach(t -> listOfStatsForIncome
                .add(new CategoryStatistics(t, 0.00)));
        modelTotalFilteredExpenses.addListener(new ListChangeListener<Expense>() {
            @Override
            public void onChanged(Change<? extends Expense> change) {
                updateListOfStats();
            }
        });
        modelTotalFilteredIncomes.addListener(new ListChangeListener<Income>() {
            @Override
            public void onChanged(Change<? extends Income> change) {
                updateListOfStats();
            }
        });
        updateListOfStats();
    }

    /**
     * Default Calculation for stats which is calculation for current month.
     */
    public void updateListOfStats() {
        ObservableMap<Integer, MonthList> yearOfRecord = yearlyRecord.get(LocalDate.now().getYear());
        MonthList currentMonthList = yearOfRecord.get(LocalDate.now().getMonth().getValue());
        countStats(currentMonthList, listOfStatsForExpense);
        countStats(currentMonthList, listOfStatsForIncome);
    }

    /**
     * Recalculates statistics for a specific period of time.
     *
     * @param month the month that needs to be recalculated.
     * @param year the year that needs to be recalculated.
     */
    public void updateListOfStats(Month month, int year) {
        if (!yearlyRecord.containsKey(year)) {
            initRecords(year);
        }
        MonthList monthListToCalculate = yearlyRecord.get(year).get(month.getValue());
        countStats(monthListToCalculate, listOfStatsForExpense);
        countStats(monthListToCalculate, listOfStatsForIncome);
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForExpense() {
        return listOfStatsForExpense;
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForIncome() {
        return listOfStatsForIncome;
    }

    /**
     * Recalculates statistics for a specific period of time.
     *
     * @param currentMonthList the month that needs to be recalculated.
     * @param typeOfCategory the list of categories that need to be recalculated whether it be income or expense.
     */
    private void countStats(MonthList currentMonthList, ObservableList<CategoryStatistics> typeOfCategory) {
        for (int i = 0; i < typeOfCategory.size(); i++) {
            Category toVerifyCat = typeOfCategory.get(i).getCategory();
            double amountVerified = currentMonthList.updateListOfStats(toVerifyCat);
            CategoryStatistics toCheck = new CategoryStatistics(toVerifyCat, amountVerified);
            if (!typeOfCategory.get(i).equals(toCheck)) {
                typeOfCategory.set(i, toCheck);
            }
        }
    }

}
