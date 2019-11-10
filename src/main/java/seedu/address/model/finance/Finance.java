package seedu.address.model.finance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Finance {

    private final List<Budget> budgets = new ArrayList<>();

    public Finance(List<Budget> budgets) {
        requireAllNonNull(budgets);
        this.budgets.addAll(budgets);
    }

    public Finance() {

    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public ObservableList<Budget> getBudgetObservableList() {
        ObservableList<Budget> internalList = FXCollections.observableArrayList(budgets);
        return internalList;
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Finance // instanceof handles nulls
                && getBudgets().equals(((Finance) other).getBudgets())); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgets);
    }
}
