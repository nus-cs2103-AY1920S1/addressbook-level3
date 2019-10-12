package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;

class SampleDataUtilTest {
    @Test
    public void getSampleCatalog_containsSampleBooks() {
        Catalog sampleAb = new Catalog();
        Arrays.stream(SampleDataUtil.getSampleBooks()).forEach(book -> sampleAb.addBook(book));
        assertEquals(sampleAb, SampleDataUtil.getSampleCatalog());
    }

    @Test
    public void getSampleLoanRecords_returnLoanRecords() {
        // update when we have sample loan records
        assertEquals(SampleDataUtil.getSampleLoanRecords(), new LoanRecords());
    }

    @Test
    public void getSampleBorrowerRecords_returnBorrowerRecords() {
        // update when we have sample loan records
        assertEquals(SampleDataUtil.getSampleBorrowerRecords(), new BorrowerRecords());
    }
}
