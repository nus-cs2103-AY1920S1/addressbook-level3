package seedu.address.model.budget;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Status;
import seedu.address.model.income.Income;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Represents the current budget of the user.
 */

public class Budget {

    private List<Claim> claimList;
    private List<Income> incomeList;
    private boolean isOverBudget;
    private double totalIncome = 0;
    private double totalExpenses = 0;
    private double budgetAmount;
    private Locale locale = new Locale("en", "US");
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);


    public Budget(List<Claim> claimList, List<Income> incomeList) {
        this.claimList = claimList;
        this.incomeList = incomeList;
    }

    /**
     * Calculates current budget for users.
     */

    public void calculateBudget() {
        calculateTotalIncome();
        calculateTotalExpenses();
        budgetAmount = totalIncome - totalExpenses;
        checkIfOverBudget(budgetAmount);
    }

    private void calculateTotalIncome() {
        for (Income income : incomeList) {
            totalIncome += Double.parseDouble(income.getAmount().value);
        }
    }

    private void calculateTotalExpenses() {
        for (Claim claim : claimList) {
            if (claim.getStatus() == Status.APPROVED) {
                totalExpenses += Double.parseDouble(claim.getAmount().value);
            }
        }
    }

    private void checkIfOverBudget(double budget) {
        isOverBudget = budget < 0;
    }

    public String getTotalIncome() {
        return currencyFormatter.format(totalIncome);
    }

    public String getTotalExpenses() {
        return currencyFormatter.format(totalExpenses);
    }

    public String getBudgetAmount() {
        return currencyFormatter.format(budgetAmount);
    }

    public boolean isOverBudget() {
        return isOverBudget;
    }
}
