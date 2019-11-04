package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LoanRecords;
import seedu.address.model.loan.Loan;

/**
 * A utility class containing a list of {@code Loan} objects to be used in tests.
 */
public class TypicalLoans {

    public static final Loan LOAN_1 = new LoanBuilder().withLoanId("L000001").withSerialNumber("B00001")
            .withBorrowerId("K0001").withStartDate("2019-10-13").withDueDate("2019-10-27").build();
    public static final Loan LOAN_2 = new LoanBuilder().withLoanId("L000002").withSerialNumber("B00002")
            .withBorrowerId("K0002").withStartDate("2019-09-01").withDueDate("2019-10-15").build();
    public static final Loan LOAN_3 = new LoanBuilder().withLoanId("L000003").withSerialNumber("B00003")
            .withBorrowerId("K0003").withStartDate("2019-10-20").withDueDate("2019-11-03").build();
    public static final Loan LOAN_4 = new LoanBuilder().withLoanId("L000004").withSerialNumber("B00004")
            .withBorrowerId("K0004").withStartDate("2019-11-05").withDueDate("2019-11-19").build();
    public static final Loan LOAN_5 = new LoanBuilder().withLoanId("L000005").withSerialNumber("B00005")
            .withBorrowerId("K0005").withStartDate("2019-08-09").withDueDate("2019-08-23").build();
    public static final Loan LOAN_6 = new LoanBuilder().withLoanId("L000006").withSerialNumber("B00006")
            .withBorrowerId("K0006").withStartDate("2019-09-20").withDueDate("2019-10-20").build();

    public static final Loan LOAN_7 = new LoanBuilder().withLoanId("L000007").withSerialNumber("B00007")
            .withBorrowerId("K0009").withStartDate("2090-10-13").withDueDate("2090-10-27").build();
    public static final Loan LOAN_7_RETURNED = new LoanBuilder(LOAN_7).withReturnDate("2019-10-20").build();

    public static final Loan LOAN_8 = new LoanBuilder().withLoanId("L000008").withSerialNumber("B00008")
            .withBorrowerId("K0008").withStartDate("2019-09-01").withDueDate("2019-09-30")
            .withReturnDate("2019-09-15").withRemainingFineAmount(150).build();
    public static final Loan LOAN_9 = new LoanBuilder().withLoanId("L000009").withSerialNumber("B00009")
            .withBorrowerId("K0009").withStartDate("2019-09-01").withDueDate("2019-09-30")
            .withReturnDate("2019-09-15").withRemainingFineAmount(300).build();

    public static final Loan LOAN_1_UNEXPIRED = new LoanBuilder().withLoanId("L000010").withSerialNumber("B00001")
            .withBorrowerId("K0001").withStartDate("2090-10-13").withDueDate("2090-10-27").build();


    // prevents instantiation
    private TypicalLoans() {
    }

    public static List<Loan> getTypicalLoans() {
        return new ArrayList<>(Arrays.asList(LOAN_1, LOAN_2, LOAN_3, LOAN_4, LOAN_5, LOAN_6));
    }

    /**
     * Returns a {@code LoanRecords} with all the typical Loans.
     */
    public static LoanRecords getTypicalLoanRecords() {
        LoanRecords loanRecords = new LoanRecords();
        getTypicalLoans().forEach(loan -> loanRecords.addLoan(loan));
        return loanRecords;
    }
}
