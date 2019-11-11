package seedu.ichifund.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_ANIME;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_FOOD;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_OVERALL;
import static seedu.ichifund.testutil.TypicalFundBook.getTypicalFundBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.budget.exceptions.DuplicateBudgetException;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.testutil.BudgetBuilder;
import seedu.ichifund.testutil.LoanBuilder;
import seedu.ichifund.testutil.RepeaterBuilder;
import seedu.ichifund.testutil.TransactionBuilder;

public class FundBookTest {

    private final FundBook fundBook = new FundBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), fundBook.getBudgetList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fundBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFundBook_replacesData() {
        FundBook newData = getTypicalFundBook();
        fundBook.resetData(newData);
        assertEquals(newData, fundBook);
    }

    @Test
    public void resetData_withDuplicateBudgets_throwsDuplicateBudgetException() {
        // Two budgets with the same identity fields
        Budget editedAnime = new BudgetBuilder(BUDGET_ANIME)
                .withAmount(BUDGET_FOOD.getAmount().toString())
                .build();
        Repeater repeater = new RepeaterBuilder().build();
        Transaction transaction = new TransactionBuilder().build();
        Loan loan = new LoanBuilder().build();
        RepeaterUniqueId currentRepeaterUniqueId = new RepeaterUniqueId("0");
        LoanId loanId = new LoanId("0");
        List<Budget> newBudgets = Arrays.asList(BUDGET_ANIME, editedAnime);
        List<Repeater> repeaters = Collections.singletonList(repeater);
        List<Transaction> transactions = Collections.singletonList(transaction);
        List<Loan> loans = Collections.singletonList(loan);
        FundBookStub newData = new FundBookStub(currentRepeaterUniqueId, loanId,
                repeaters, newBudgets, transactions, loans);
        assertThrows(DuplicateBudgetException.class, () -> fundBook.resetData(newData));
    }

    @Test
    public void hasBudget_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fundBook.hasBudget(null));
    }

    @Test
    public void hasBudget_budgetNotInFundBook_returnsFalse() {
        assertFalse(fundBook.hasBudget(BUDGET_OVERALL));
    }

    @Test
    public void hasBudget_budgetInFundBook_returnsTrue() {
        fundBook.addBudget(BUDGET_ANIME);
        assertTrue(fundBook.hasBudget(BUDGET_ANIME));
    }

    @Test
    public void hasBudget_budgetWithSameIdentityFieldsInFundBook_returnsTrue() {
        fundBook.addBudget(BUDGET_ANIME);
        Budget editedAnime = new BudgetBuilder(BUDGET_ANIME).withAmount(BUDGET_FOOD.getAmount().toString()).build();
        assertTrue(fundBook.hasBudget(editedAnime));
    }

    @Test
    public void getBudgetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> fundBook.getTransactionList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> fundBook.getRepeaterList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> fundBook.getBudgetList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> fundBook.getDataList().remove(0));
    }

    /**
     * A stub ReadOnlyFundBook whose lists can violate interface constraints.
     */
    private static class FundBookStub implements ReadOnlyFundBook {
        private RepeaterUniqueId currentRepeaterUniqueId = new RepeaterUniqueId("0");
        private LoanId currentLoanId = new LoanId("0");
        private final ObservableList<Repeater> repeaters = FXCollections.observableArrayList();
        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        private final ObservableList<Data> datas = FXCollections.observableArrayList();
        private final ObservableList<Loan> loans = FXCollections.observableArrayList();

        FundBookStub(RepeaterUniqueId currentRepeaterUniqueId, LoanId currentLoanId,
                     Collection<Repeater> repeaters, Collection<Budget> budgets,
                     Collection<Transaction> transactions, Collection<Loan> loans) {
            this.currentRepeaterUniqueId = currentRepeaterUniqueId;
            this.currentLoanId = currentLoanId;
            this.repeaters.setAll(repeaters);
            this.budgets.setAll(budgets);
            this.transactions.setAll(transactions);
            this.loans.setAll(loans);
        }

        @Override
        public RepeaterUniqueId getCurrentRepeaterUniqueId() {
            return currentRepeaterUniqueId;
        }

        @Override
        public LoanId getCurrentLoanId() {
            return currentLoanId;
        }

        @Override
        public ObservableList<Repeater> getRepeaterList() {
            return repeaters;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return transactions;
        }

        @Override
        public ObservableList<Data> getDataList() {
            return datas;
        }

        @Override
        public ObservableList<Loan> getLoanList() {
            return loans;
        }
    }
}
