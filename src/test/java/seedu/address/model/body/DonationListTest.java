package seedu.address.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class DonationListTest {
    @Test
    void enumerateDonationListTest_indexOne_correct() {
        assertEquals(DonationList.KIDNEYS.toString(), "KIDNEYS");
    }

    @Test
    void enumerateDonationListTest_indexOne_wrong() {
        assertNotEquals(DonationList.KIDNEYS.toString(), "kidneys");
    }

}
