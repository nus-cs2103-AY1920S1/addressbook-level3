package seedu.address.model.finance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;


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

    public Budget removeBudget(Index index) {
        requireNonNull(index);
        return budgets.remove(index.getZeroBased());
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
