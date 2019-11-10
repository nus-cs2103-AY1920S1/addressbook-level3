package seedu.address.model.finance;

import seedu.address.model.util.SortingOrder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Budget {
    private final String name;
    private final Money amount;
    private final List<Spending> spendings = new ArrayList<>();
    private Money remainingAmount;
    public static final String MESSAGE_CONSTRAINTS = "Budget should have a name followed by an amount in the form 99.99";

    public Budget(String name, Money amount, List<Spending> spendings) {
        requireAllNonNull(name, amount, spendings);
        this.name = name;
        this.amount = amount;
        this.spendings.addAll(spendings);
        Collections.sort(spendings, SortingOrder.getCurrentSortingOrderForSpending());
        this.remainingAmount = calculateRemaining(amount, spendings);
    }

    /**
     * The project has spend on something under this type of budget.
     * Add a spending object to the list of spendings
     * @param spending A spending object
     */
    public void addSpending(Spending spending) {
        spendings.add(spending);
        Collections.sort(spendings, SortingOrder.getCurrentSortingOrderForSpending());
        remainingAmount = new Money(remainingAmount.getAmount().subtract(spending.getMoney().getAmount()));
    }

    public Money getMoney() {
        return amount;
    }

    public Money getRemainingMoney() {
        return remainingAmount;
    }

    public String getName() {
        return name;
    }

    public List<Spending> getSpendings() {
        return spendings;
    }

    public Money calculateRemaining(Money amount, List<Spending> spendings) {
        BigDecimal result = amount.getAmount();
        for (Spending spending : spendings) {
            result = result.subtract(spending.getMoney().getAmount());
        }
        return new Money(result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Collections.sort(spendings, SortingOrder.getCurrentSortingOrderForSpending());
        sb.append(String.format("Budget %s has $%s remaining: ", name, amount.toString()));
        for (Spending spending : spendings) {
            sb.append("\n " + spending.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getMoney().equals(getMoney())
                && otherBudget.getName().equals(getName())
                && otherBudget.getSpendings().equals(getSpendings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, spendings);
    }
}
