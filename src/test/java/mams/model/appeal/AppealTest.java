package mams.model.appeal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mams.testutil.TypicalAppeals;
public class AppealTest {

    @Test
    public void isSameAppeal() {
        // same object -> returns true
        Assertions.assertTrue(TypicalAppeals.APPEAL1.isSameAppeal(TypicalAppeals.APPEAL1));

        // null -> returns false
        Assertions.assertFalse(TypicalAppeals.APPEAL1.isSameAppeal(null));

    }

    @Test void isValidAppealId() {
        //invalid code
        assertFalse(Appeal.isValidAppealId("")); // empty string
        assertFalse(Appeal.isValidAppealId("CS")); // C with another letter only
        assertFalse(Appeal.isValidAppealId("C23")); // less than 6 numbers
        assertFalse(Appeal.isValidAppealId("C1233333314")); // more than 6 numbers

        //valid code
        assertTrue(Appeal.isValidAppealId("C000002"));
        assertTrue(Appeal.isValidAppealId("C123456"));
        assertTrue(Appeal.isValidAppealId("C123232"));


    }

    @Test void isValidAppeealType() {
        //invalid code
        assertFalse(Appeal.isValidAppealType("")); // empty string
        assertFalse(Appeal.isValidAppealType("addmodule")); // no spaces
        assertFalse(Appeal.isValidAppealType("drop  module")); // more than 1 space
        assertFalse(Appeal.isValidAppealType("increase workload2")); // with numbers

        //valid code
        assertTrue(Appeal.isValidAppealType("add MOdule"));
        assertTrue(Appeal.isValidAppealType("DroP mOdUle"));
        assertTrue(Appeal.isValidAppealType("INCREASE WORKLOAD"));
    }

    @Test
    public void isEqual() {

    }
}
