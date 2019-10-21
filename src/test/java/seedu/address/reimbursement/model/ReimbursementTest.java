package seedu.address.reimbursement.model;

import org.junit.jupiter.api.Test;

import static seedu.address.testutil.Assert.assertThrows;

public class ReimbursementTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reimbursement(null));
    }

}
