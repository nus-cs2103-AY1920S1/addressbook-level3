package seedu.address.model.statistics;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;


/**
 * Handles calculation of statistics.
 */
public class StatisticsManager implements Statistics {
    private double lastMonthExpenses;
    private ObservableMap<Integer, MonthList> monthlyRecord;
    private ObservableList<CategoryStatistics> listOfStatsForExpense;
    private ObservableList<CategoryStatistics> listOfStatsForIncome;
    private FilteredList<Expense> modelTotalFilteredExpenses;
    private FilteredList<Income> modelTotalFilteredIncomes;
    private DailyList currentDailyList;
    private CategoryList listOfCategories;

    /**
     * Manages the general Statistics.
     */
    public StatisticsManager(Model modelManager) {
        this.modelTotalFilteredExpenses = new FilteredList(modelManager.getFilteredExpenses());
        this.modelTotalFilteredIncomes = new FilteredList(modelManager.getFilteredIncomes());
        this.listOfCategories = modelManager.getCategoryList();
        int currentMonth = LocalDate.now().getMonth().getValue();
        int currentDay = LocalDate.now().getDayOfMonth();
        int currentYear = LocalDate.now().getYear();
        monthlyRecord = FXCollections.observableHashMap();
        listOfStatsForExpense = FXCollections.observableArrayList();
        listOfStatsForIncome = FXCollections.observableArrayList();
        initRecords(currentMonth, currentYear);
        initStats();
    }

    /**
     * Loads the Records from scratch.
     */
    private void initRecords(int month, int currentYear) {
        for (int i = 1; i <= month; i++) {
            FilteredList<Expense> filteredExpenseByMonth = modelTotalFilteredExpenses;
            FilteredList<Income> filteredIncomeByMonth = modelTotalFilteredIncomes;
            FilteredList<Expense> filteredExpenses = new FilteredList<>(filteredExpenseByMonth, new EntryTimeContainsPredicate(i));
            MonthList monthToCompare = new MonthList(listOfCategories, filteredExpenses, filteredIncomeByMonth,
                    Month.of(i), currentYear);
            monthlyRecord.put(i, monthToCompare);
        }
    }

    private void initStats() {
        listOfCategories.getInternalListForOtherEntries().stream().forEach(t -> listOfStatsForExpense.
                add(new CategoryStatistics(t,0.00)));
        listOfCategories.getInternalListForIncome().stream().forEach(t -> listOfStatsForIncome.
                add(new CategoryStatistics(t,0.00)));
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

    public void updateListOfStats() {
        MonthList currentMonthList = monthlyRecord.get(LocalDate.now().getMonth().getValue());
        System.out.println("STatsManager");
        countStats(currentMonthList, listOfStatsForExpense);
        countStats(currentMonthList, listOfStatsForIncome);
        System.out.println("StatsManagerDone");
        listOfStatsForExpense.stream().forEach(cat -> System.out.println("CategoryName Is " + cat.getCategoryName()
                + "Value is" + cat.getAmountCalculated()));
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForExpense() {
        return listOfStatsForExpense;
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForIncome() {
        return listOfStatsForIncome;
    }

    private void countStats(MonthList currentMonthList, ObservableList<CategoryStatistics> typeOfCategory) {
        for(int i = 0; i < typeOfCategory.size(); i++) {
            Category toVerifyCat = typeOfCategory.get(i).getCategory();
            double amountVerified = currentMonthList.updateListOfStats(toVerifyCat);
            CategoryStatistics toCheck = new CategoryStatistics(toVerifyCat, amountVerified);
            if (!typeOfCategory.get(i).equals(toCheck)) {
                typeOfCategory.set(i, toCheck);
            }
        }
    }

    public void testStats() {
        listOfStatsForExpense.stream().forEach(cat -> System.out.println("CategoryName Is " + cat.getCategoryName()
                + "Value is" + cat.getAmountCalculated()));
    }

//    public void updateListOfStats(LocalDate startPeriod, LocalDate endPeriod);

//    public void getTagsForTimePeriod() {
//
//    }



}
