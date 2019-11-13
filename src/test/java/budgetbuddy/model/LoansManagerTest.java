package budgetbuddy.model;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanFilters;
import budgetbuddy.model.loan.LoanSorters;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.exceptions.DuplicateLoanException;
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;
import budgetbuddy.model.loan.predicates.AmountMatchPredicate;
import budgetbuddy.model.loan.predicates.DateMatchPredicate;
import budgetbuddy.model.loan.predicates.DescriptionMatchPredicate;
import budgetbuddy.model.loan.predicates.PersonMatchPredicate;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalDebtors;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoansManagerTest {

    private LoansManager loansManager = new LoansManager(TypicalLoans.LOAN_LIST);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoansManager(null));
    }

    @Test
    public void updateFilteredList_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> loansManager.updateFilteredList(null));
    }

    @Test
    public void updateFilteredList_validPredicates_loanListFiltersCorrectly() {
        // direction predicate
        loansManager.updateFilteredList(LoanFilters.getDirectionPredicate(Direction.IN));
        assertTrue(loansManager.getFilteredLoans().stream().allMatch(loan -> loan.getDirection() == Direction.IN));

        // status predicate
        loansManager.updateFilteredList(LoanFilters.getStatusPredicate(Status.PAID));
        assertTrue(loansManager.getFilteredLoans().stream().allMatch(Loan::isPaid));

        // amount predicate
        loansManager.updateFilteredList(new AmountMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getAmount()));
        assertTrue(loansManager.getFilteredLoans().stream()
                .allMatch(loan -> loan.getAmount().equals(TypicalLoans.JOHN_OUT_UNPAID.getAmount())));

        // date predicate
        loansManager.updateFilteredList(new DateMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDate()));
        assertTrue(loansManager.getFilteredLoans().stream()
                .allMatch(loan -> loan.getDate().equals(TypicalLoans.JOHN_OUT_UNPAID.getDate())));

        // description predicate
        loansManager.updateFilteredList(new DescriptionMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDescription()));
        assertTrue(loansManager.getFilteredLoans().stream()
                .allMatch(loan -> loan.getDescription().equals(TypicalLoans.JOHN_OUT_UNPAID.getDescription())));

        // person predicate
        loansManager.updateFilteredList(new PersonMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getPerson()));
        assertTrue(loansManager.getFilteredLoans().stream()
                .allMatch(loan -> loan.getPerson().equals(TypicalLoans.JOHN_OUT_UNPAID.getPerson())));

        // show-all predicate
        loansManager.updateFilteredList(LoanFilters.FILTER_ALL);
        assertEquals(loansManager.getFilteredLoans(), TypicalLoans.LOAN_LIST);
    }

    @Test
    public void sortLoans_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> loansManager.sortLoans(null));
    }

    @Test
    public void sortLoans_validSorters_loanListSortsCorrectly() {
        // sort by amount in ascending order
        loansManager.sortLoans(LoanSorters.AMOUNT_ASC);
        assertTrue(IntStream.range(0, loansManager.getFilteredLoans().size() - 1)
                .allMatch(i -> loansManager.getFilteredLoans().get(i).getAmount().toLong()
                        <= loansManager.getFilteredLoans().get(i + 1).getAmount().toLong()));

        // sort by date, newest first
        loansManager.sortLoans(LoanSorters.DATE_NEWEST);
        assertTrue(IntStream.range(0, loansManager.getFilteredLoans().size() - 1).allMatch(i -> {
            LocalDate first = loansManager.getFilteredLoans().get(i).getDate();
            LocalDate second = loansManager.getFilteredLoans().get(i + 1).getDate();
            return first.isAfter(second) || first.isEqual(second);
        }));

        // sort by persons' names in alphabetical order
        loansManager.sortLoans(LoanSorters.PERSON);
        assertTrue(IntStream.range(0, loansManager.getFilteredLoans().size() - 1)
                .allMatch(i -> loansManager.getFilteredLoans().get(i).getPerson().getName().toString()
                .compareTo(loansManager.getFilteredLoans().get(i + 1).getPerson().getName().toString()) <= 0));
    }

    @Test
    public void sortLoans_doubleSort_loanListSortOrderReverses() {
        // sort by amount in descending order
        loansManager.sortLoans(LoanSorters.AMOUNT_ASC);
        loansManager.sortLoans(LoanSorters.AMOUNT_ASC);
        assertTrue(IntStream.range(0, loansManager.getFilteredLoans().size() - 1)
                .allMatch(i -> loansManager.getFilteredLoans().get(i).getAmount().toLong()
                        >= loansManager.getFilteredLoans().get(i + 1).getAmount().toLong()));

        // sort by date, oldest first
        loansManager.sortLoans(LoanSorters.DATE_NEWEST);
        loansManager.sortLoans(LoanSorters.DATE_NEWEST);
        assertTrue(IntStream.range(0, loansManager.getFilteredLoans().size() - 1).allMatch(i -> {
            LocalDate first = loansManager.getFilteredLoans().get(i).getDate();
            LocalDate second = loansManager.getFilteredLoans().get(i + 1).getDate();
            return first.isBefore(second) || first.isEqual(second);
        }));

        // sort by persons' names in reverse alphabetical order
        loansManager.sortLoans(LoanSorters.PERSON);
        loansManager.sortLoans(LoanSorters.PERSON);
        assertTrue(IntStream.range(0, loansManager.getFilteredLoans().size() - 1)
                .allMatch(i -> loansManager.getFilteredLoans().get(i).getPerson().getName().toString()
                        .compareTo(loansManager.getFilteredLoans().get(i + 1).getPerson().getName().toString()) >= 0));
    }

    @Test
    public void getLoans_removeLoan_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> loansManager.getLoans().remove(0));
    }

    @Test
    public void getLoan_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> loansManager.getLoan(null));
    }

    @Test
    public void getLoan_indexBeyondListSize_throwsLoanNotFoundException() {
        assertThrows(LoanNotFoundException.class, () -> loansManager.getLoan(
                Index.fromZeroBased(loansManager.getLoansCount())));
    }

    @Test
    public void addLoan_addAfterFilter_filteredLoansShowsAllLoan() {
        int targetSize = loansManager.getLoansCount();
        loansManager.updateFilteredList(LoanFilters.getStatusPredicate(Status.PAID));
        loansManager.addLoan(new LoanBuilder().build());
        assertEquals(targetSize + 1, loansManager.getFilteredLoans().size());
    }

    @Test
    public void addLoan_duplicateLoan_throwsDuplicateLoanException() {
        assertThrows(DuplicateLoanException.class, () -> loansManager.addLoan(TypicalLoans.JOHN_OUT_UNPAID));
    }

    @Test
    public void editLoan_validIndexValidLoan_editedLoanReplacesTargetLoan() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        Loan targetLoan = loansManager.getLoan(targetIndex);
        Loan editedLoan = new LoanBuilder(targetLoan).withAmount(targetLoan.getAmount().toLong() + 1).build();

        loansManager.editLoan(targetIndex, editedLoan);
        assertTrue(loansManager.getLoans().stream().anyMatch(loan -> loan.isSameLoan(editedLoan)));
    }

    @Test
    public void editLoan_duplicateLoan_throwsDuplicateLoanException() {
        assertThrows(DuplicateLoanException.class, () -> loansManager.editLoan(
                TypicalIndexes.INDEX_THIRD_ITEM, TypicalLoans.JOHN_OUT_UNPAID));
    }

    @Test
    public void updateStatus_validIndexValidLoan_updatedLoanReplacesTargetLoan() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        Loan targetLoan = loansManager.getLoan(targetIndex);
        Loan updatedLoan = new LoanBuilder(targetLoan).withStatus("PAID").build();

        loansManager.updateStatus(targetIndex, updatedLoan);
        assertEquals(updatedLoan, loansManager.getLoan(targetIndex));
    }

    @Test
    public void deleteLoan_validIndex_loanDeletedFromList() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        Loan targetLoan = loansManager.getLoan(targetIndex);

        loansManager.deleteLoan(targetIndex);
        assertTrue(loansManager.getLoans().stream().noneMatch(loan -> loan.equals(targetLoan)));
    }

    @Test
    public void setDebtors_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> loansManager.setDebtors(null));
    }

    @Test
    public void setDebtors_validList_debtorsSortedByNameInAlphabeticalOrder() {
        loansManager.setDebtors(TypicalDebtors.DEBTOR_LIST);
        assertTrue(IntStream.range(0, loansManager.getDebtors().size() - 1).allMatch(i -> {
            Name first = loansManager.getDebtors().get(i).getDebtor().getName();
            Name second = loansManager.getDebtors().get(i + 1).getDebtor().getName();
            return first.toString().compareToIgnoreCase(second.toString()) <= 0;
        }));
    }

    @Test
    public void getDebtors_addDebtorToList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> loansManager.getDebtors().add(TypicalDebtors.JOHN));
    }

    @Test
    public void equals() {
        // same values -> returns true
        LoansManager loansManagerCopy = new LoansManager(loansManager.getLoans());
        loansManagerCopy.setDebtors(loansManager.getDebtors());
        assertEquals(loansManagerCopy, loansManager);

        // same object -> returns true
        assertEquals(loansManager, loansManager);

        // null -> returns false
        assertNotEquals(null, loansManager);

        // different types -> returns false
        assertNotEquals(5, loansManager);

        // different loan list -> returns false
        loansManager = new LoansManager(TypicalLoans.LOAN_LIST);
        LoansManager loansManagerDiffLoans = new LoansManager();
        loansManagerDiffLoans.setDebtors(loansManager.getDebtors());
        assertNotEquals(loansManagerDiffLoans, loansManager);

        // different debtors -> returns false
        loansManager.setDebtors(TypicalDebtors.DEBTOR_LIST);
        LoansManager loansManagerDiffDebtors = new LoansManager(loansManager.getLoans());
        assertNotEquals(loansManagerDiffDebtors, loansManager);
    }
}
