package seedu.ichifund.testutil;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.ReadOnlyUserPrefs;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;

/**
 * A stub for Model that throws errors for any method called.
 */
public class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getFundBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFundBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public RepeaterUniqueId getCurrentRepeaterUniqueId() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCurrentRepeaterUniqueId(RepeaterUniqueId uniqueId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTransaction(Transaction transaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTransaction(Transaction target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public TransactionContext getTransactionContext() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransactionContext(TransactionContext transactionContext) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateTransactionContext(Transaction transaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Transaction> getAssociatedTransactions(RepeaterUniqueId repeaterUniqueId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRepeater(Repeater repeater) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void createRepeaterTransactions(Repeater repeater) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRepeater(Repeater repeater) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRepeater(Repeater target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRepeaterTransactions(RepeaterUniqueId repeaterUniqueId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRepeater(Repeater target, Repeater editedRepeater) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Repeater> getFilteredRepeaterList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredRepeaterList(Predicate<Repeater> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public LoanId getCurrentLoanId() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addLoan(Loan loan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCurrentLoanId(LoanId loanId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLoan(Loan loan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLoanList(Predicate<Loan> predicate) {
        throw new AssertionError("This method should not be called.");
    }



    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLoan(Loan target, Loan editedLoan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void payLoan(Loan target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteBudget(Budget target) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyProperty<TransactionContext> getTransactionContextProperty() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Data> getDataList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateDataList(List<Data> datas) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFundBook(ReadOnlyFundBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyFundBook getFundBook() {
        throw new AssertionError("This method should not be called.");
    }
}
