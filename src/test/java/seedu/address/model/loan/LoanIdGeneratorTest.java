package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalLoans.getTypicalLoanRecords;

import org.junit.jupiter.api.Test;

import seedu.address.model.LoanRecords;

class LoanIdGeneratorTest {

    @Test
    public void generateLoanId_noLoanRecords_firstLoanId() {
        LoanIdGenerator.setLoanRecords(null);
        assertEquals(LoanIdGenerator.generateLoanId(), new LoanId("L000001"));
    }

    @Test
    public void generateLoanId_emptyLoanRecords_autoGenerateSuccess() {
        LoanIdGenerator.setLoanRecords(new LoanRecords());
        assertEquals(LoanIdGenerator.generateLoanId(), new LoanId("L000001"));
    }

    @Test
    public void generateLoanId_typicalLoanRecords_autoGenerateSuccess() {
        LoanIdGenerator.setLoanRecords(getTypicalLoanRecords());
        assertEquals(LoanIdGenerator.generateLoanId(), new LoanId("L000007"));
    }
}
