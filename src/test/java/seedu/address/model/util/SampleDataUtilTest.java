package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.loan.LoanIdGenerator;

class SampleDataUtilTest {

    @Test
    public void getSampleCatalog_containsSampleBooks() {
        Catalog catalog = new Catalog();
        Arrays.stream(SampleDataUtil.getSampleBooks()).forEach(catalog::addBook);
        assertEquals(catalog, SampleDataUtil.getSampleCatalog(SampleDataUtil.getSampleLoanRecords()));
    }

    @Test
    public void getSampleLoanRecords_returnLoanRecords() {
        LoanRecords loanRecords = new LoanRecords();
        LoanIdGenerator.setLoanRecords(loanRecords);
        SampleDataUtil.getSampleLoans().forEach(
            loanMaker -> loanRecords.addLoan(loanMaker.apply(LoanIdGenerator.generateLoanId())));
        assertEquals(loanRecords, SampleDataUtil.getSampleLoanRecords());
    }

    @Test
    public void getSampleBorrowerRecords_returnBorrowerRecords() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        Arrays.stream(SampleDataUtil.getSampleBorrowers()).forEach(borrowerRecords::addBorrower);
        assertEquals(borrowerRecords,
                SampleDataUtil.getSampleBorrowerRecords(SampleDataUtil.getSampleLoanRecords()));
    }
}
