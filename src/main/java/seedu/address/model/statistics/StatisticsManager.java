package seedu.address.model.statistics;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Date;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;


/**
 * Handles calculation of statistics.
 */
public class StatisticsManager implements Statistics {
    private ObservableMap<Integer, ObservableMap<Integer, MonthList>> yearlyRecord;
    private ObservableList<CategoryStatistics> listOfStatsForExpense;
    private ObservableList<CategoryStatistics> listOfStatsForIncome;
    private ObservableList<DailyStatistics> listOfStatsForDailyExpense;
    private ObservableList<DailyStatistics> listOfStatsForDailyIncome;
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
        listOfStatsForDailyExpense = FXCollections.observableArrayList();
        listOfStatsForDailyIncome = FXCollections.observableArrayList();
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
        updateLineCharts();
    }

    /**
     * Calculates the statistics for the current month.
     */
    public void updateListOfStats() {
        ObservableMap<Integer, MonthList> yearOfRecord = yearlyRecord.get(LocalDate.now().getYear());
        ArrayList<MonthList> listOfMonths = new ArrayList<MonthList>();
        listOfMonths.add(yearOfRecord.get(LocalDate.now().getMonth().getValue()));
        countStats(listOfMonths, listOfStatsForExpense);
        countStats(listOfMonths, listOfStatsForIncome);
    }

    /**
     * Calculates the statistics for the specified month or the range of periods given.
     *
     * @param listOfPeriods contains the range of periods that was specified by the user.
     */
    public void updateListOfStats(List<Date> listOfPeriods) {
        if (listOfPeriods.size() == 1) {
            calculationsForOneMonth(listOfPeriods.get(0));
        } else {
            calculationsForRangeOfPeriods(listOfPeriods);
        }
    }

    public void updateLineCharts() {
        ObservableMap<Integer, MonthList> yearOfRecord = yearlyRecord.get(LocalDate.now().getYear());
        MonthList monthListToCarryOutMonthlyCalculations = yearOfRecord.get(LocalDate.now().getMonth().getValue());
        monthListToCarryOutMonthlyCalculations.calculateBarChart(this.listOfStatsForDailyIncome);
        monthListToCarryOutMonthlyCalculations.calculateBarChart(this.listOfStatsForDailyExpense)

    }

    public void updateLineCharts(Date monthToShow) {

    }

    /**
     * Recalculates statistics for one month only.
     *
     * @param monthToCalculate the date which contains the format of what is needed to calculate.
     */
    private void calculationsForOneMonth(Date monthToCalculate) {
        int yearToCheck = monthToCalculate.getDate().getYear();
        if (!yearlyRecord.containsKey(yearToCheck)) {
            initRecords(yearToCheck);
        }
        ArrayList<MonthList> listOfMonths = new ArrayList<MonthList>();
        Month month = monthToCalculate.getDate().getMonth();
        MonthList startMonthListToCalculate = yearlyRecord.get(yearToCheck).get(month.getValue());
        listOfMonths.add(startMonthListToCalculate);
        countStats(listOfMonths, listOfStatsForExpense);
        countStats(listOfMonths, listOfStatsForIncome);
    }

    /**
     * Recalculates statistics for a range of periods if the user specified a huge range of periods.
     *
     * @param rangeOfDates a list of size 2 that contains the range of dates that needs to be calculated.
     */
    private void calculationsForRangeOfPeriods(List<Date> rangeOfDates) {
        Date startDate = rangeOfDates.get(0);
        Date endDate = rangeOfDates.get(1);
        int startYear = startDate.getDate().getYear();
        int endYear = endDate.getDate().getYear();
        ArrayList<MonthList> listOfMonths = new ArrayList<MonthList>();
        for (int i = startYear; i <= endYear; i++) {
            if (!yearlyRecord.containsKey(i)) {
                initRecords(i);
            }

            if (startYear == endYear) {
                listOfMonths.addAll(calculateforSpecifiedMonths(startDate.getDate().getMonth().getValue(),
                        endDate.getDate().getMonth().getValue(), startYear));
            } else {
                listOfMonths.addAll(calculateforSpecifiedMonths(1, 12, i));
            }
        }
        assert(listOfMonths.size() != 0);
        countStats(listOfMonths, listOfStatsForExpense);
        countStats(listOfMonths, listOfStatsForIncome);
    }

    /**
     * Recalculates statistics for the months in a year. Helper method to SLAP.
     *
     * @param startMonth the Month which needs to start calculating from.
     * @param endMonth the Month which needs to end calculating from.
     * @param year the year in which the calculation is taking place.
     * @return listOfMonths the particular monthlist that needs to be calculated.
     */
    private ArrayList<MonthList> calculateforSpecifiedMonths(int startMonth, int endMonth, int year) {
        ArrayList<MonthList> listOfMonths = new ArrayList<MonthList>();
        for (int i = startMonth; i <= endMonth; i++) {
            MonthList monthListToCalculate = yearlyRecord.get(year).get(i);
            listOfMonths.add(monthListToCalculate);
        }
        return listOfMonths;
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
     * @param currentMonthList the lists of months that needs to be recalculated.
     * @param typeOfCategory the list of categories that need to be recalculated whether it be income or expense.
     */
    private void countStats(ArrayList<MonthList> currentMonthList, ObservableList<CategoryStatistics> typeOfCategory) {
        for (int i = 0; i < typeOfCategory.size(); i++) {
            Category toVerifyCat = typeOfCategory.get(i).getCategory();
            double totalForCategoryOverMonths = countStatsForMonth(currentMonthList, toVerifyCat);
            CategoryStatistics toCheck = new CategoryStatistics(toVerifyCat, totalForCategoryOverMonths);
            if (!typeOfCategory.get(i).equals(toCheck)) {
                typeOfCategory.set(i, toCheck);
            }
        }
    }

    /**
     * Recalculates statistics for a Category for all the months in the period. Helper method to SLAP.
     *
     * @param currentMonthList the lists of months that needs to be recalculated.
     * @param category the category that need to be recalculated whether it be income or expense.
     */
    private double countStatsForMonth(ArrayList<MonthList> currentMonthList, Category category) {
        double totalAmountForTotalMonths = 0.00;
        for (int k = 0; k < currentMonthList.size(); k++) {
            MonthList monthToCalculate = currentMonthList.get(k);
            totalAmountForTotalMonths = totalAmountForTotalMonths + monthToCalculate.updateListOfStats(category);
        }
        return totalAmountForTotalMonths;
    }

}
