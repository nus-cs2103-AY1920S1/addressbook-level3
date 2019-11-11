package mams.model.appeal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mams.testutil.TypicalAppeals;
public class AppealTest {

    @Test
    public void isSameAppeal() {

        Appeal appealTest1 = TypicalAppeals.APPEAL1;

        // same object -> returns true
        Assertions.assertTrue(appealTest1.isSameAppeal(appealTest1));

        //null -> returns false
        Assertions.assertFalse(appealTest1.isSameAppeal(null));

        // approved appeal -> returns false
        Appeal approveAppeal = new Appeal(appealTest1.getAppealId(),
                appealTest1.getAppealType(),
                appealTest1.getStudentId(),
                appealTest1.getAcademicYear(),
                appealTest1.getStudentWorkload(),
                appealTest1.getAppealDescription(),
                appealTest1.getPreviousModule(),
                appealTest1.getNewModule(),
                appealTest1.getModuleToAdd(),
                appealTest1.getModuleToDrop(),
                true,
                "approved",
                "");
        assertFalse(appealTest1.isSameAppeal(approveAppeal));

        // rejected appeal -> returns false
        Appeal rejectedAppeal = new Appeal(appealTest1.getAppealId(),
                appealTest1.getAppealType(),
                appealTest1.getStudentId(),
                appealTest1.getAcademicYear(),
                appealTest1.getStudentWorkload(),
                appealTest1.getAppealDescription(),
                appealTest1.getPreviousModule(),
                appealTest1.getNewModule(),
                appealTest1.getModuleToAdd(),
                appealTest1.getModuleToDrop(),
                true,
                "rejected",
                "");
        assertFalse(appealTest1.isSameAppeal(rejectedAppeal));

    }

    @Test
    public void equals() {
        Appeal appealToTest = TypicalAppeals.APPEAL1;

        // same object -> returns true
        assertTrue(appealToTest.equals(appealToTest));

        // null -> returns false
        assertFalse(appealToTest.equals(null));

        // different type -> return false
        assertFalse(appealToTest.equals(5));

        // different appeal -> return  false
        Appeal anotherAppeal = TypicalAppeals.APPEAL2;
        assertFalse(appealToTest.equals(anotherAppeal));

        // different appealID -> return false
        Appeal appealwithDifferentId = new Appeal(TypicalAppeals.APPEAL3.getAppealId(),
                appealToTest.getAppealType(),
                appealToTest.getStudentId(),
                appealToTest.getAcademicYear(),
                appealToTest.getStudentWorkload(),
                appealToTest.getAppealDescription(),
                appealToTest.getPreviousModule(),
                appealToTest.getNewModule(),
                appealToTest.getModuleToAdd(),
                appealToTest.getModuleToDrop(),
                false,
                "",
                "");
        assertFalse(appealToTest.equals(appealwithDifferentId));

        // different appeal type -> returns false
        Appeal appealOfAnotherType = new Appeal(appealToTest.getAppealId(),
                TypicalAppeals.APPEAL2.getAppealType(),
                appealToTest.getStudentId(),
                appealToTest.getAcademicYear(),
                appealToTest.getStudentWorkload(),
                appealToTest.getAppealDescription(),
                appealToTest.getPreviousModule(),
                appealToTest.getNewModule(),
                appealToTest.getModuleToAdd(),
                appealToTest.getModuleToDrop(),
                false,
                "",
                "");
        assertFalse(appealToTest.equals(appealOfAnotherType));

        // different studentID -> returns false
        Appeal appealWithAnotherStudent = new Appeal(appealToTest.getAppealId(),
                appealToTest.getAppealType(),
                TypicalAppeals.APPEAL3.getStudentId(),
                appealToTest.getAcademicYear(),
                appealToTest.getStudentWorkload(),
                appealToTest.getAppealDescription(),
                appealToTest.getPreviousModule(),
                appealToTest.getNewModule(),
                appealToTest.getModuleToAdd(),
                appealToTest.getModuleToDrop(),
                false,
                "",
                "");
        assertFalse(appealToTest.equals(appealWithAnotherStudent));
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

    @Test void isValidAppealType() {
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
        assertFalse(Appeal.isValidAppealType("")); // empty string
        assertFalse(Appeal.isValidAppealType("addmodule")); // no spaces
        assertFalse(Appeal.isValidAppealType("drop  module")); // more than 1 space
        assertFalse(Appeal.isValidAppealType("increase workload2")); // with numbers

        //valid code
        assertTrue(Appeal.isValidAppealType("add MOdule"));
        assertTrue(Appeal.isValidAppealType("DroP mOdUle"));
        assertTrue(Appeal.isValidAppealType("INCREASE WORKLOAD"));
    }
}
