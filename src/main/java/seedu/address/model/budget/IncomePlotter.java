package seedu.address.model.budget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import seedu.address.model.commonvariables.Date;
import seedu.address.model.income.Income;

/**
 *  Represents the income portion of the Budget graph
 */

public class IncomePlotter {
    private List<Income> incomeList;
    private double startingIncome = 0;
    private final LocalDate currentLocalDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String currentDateString = currentLocalDate.format(formatter);
    private Date currentDate = new Date(currentDateString);
    private XYSeries incomeSeries = new XYSeries("Income");
    private int currentMonthNumber = currentDate.date.getMonthValue();
    private int currentYearNumber = currentDate.date.getYear();

    IncomePlotter(List<Income> incomeList) {
        this.incomeList = incomeList;
    }

    /**
     * Plots points of income values
     */

    XYSeries plotIncomes() {
        findIncomeValueAtStartOfMonth();
        incomeSeries.add(1, startingIncome);
        double currentIncome = startingIncome;
        List<Income> approvedIncomesInCurrentMonthList = findIncomesInCurrentMonth();
        for (int day = 2; day <= 30; day++) {
            for (Income income : approvedIncomesInCurrentMonthList) {
                if (income.getDate().date.getDayOfMonth() == day) {
                    currentIncome += Double.parseDouble(income.getAmount().value);
                }
            }
            incomeSeries.add(day, currentIncome);
        }
        return incomeSeries;
    }

    /**
     * Finds the income value at the start of the month
     */

    private void findIncomeValueAtStartOfMonth() {
        LocalDate firstDayOfMonth = LocalDate.of(currentYearNumber, currentMonthNumber, 2);
        for (Income income : incomeList) {
            if (income.getDate().date.isBefore(firstDayOfMonth)) {
                startingIncome += Double.parseDouble(income.getAmount().value);
            }
        }
    }

    /**
     * Returns an updated list of incomes that are in the current month
     */

    private List<Income> findIncomesInCurrentMonth() {
        List<Income> updatedIncomeList = new ArrayList<>();
        for (Income income : incomeList) {
            if (currentMonthNumber == income.getDate().date.getMonthValue()) {
                updatedIncomeList.add(income);
            }
        }
        return updatedIncomeList;
    }
}

