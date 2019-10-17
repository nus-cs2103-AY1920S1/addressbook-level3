package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DepartmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    public void constructor_invalidDepartment_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Department(""));
        assertThrows(IllegalArgumentException.class, () -> new Department(" Logistics"));
    }

    @Test
    public void isValidDepartment() {
        // null string
        assertThrows(NullPointerException.class, () -> Department.isValidDepartment(null));

        // invalid string -> return false
        assertFalse(Department.isValidDepartment(" "));
        assertFalse(Department.isValidDepartment(" Marketing"));

        // valid string -> return true
        assertTrue(Department.isValidDepartment("Marking"));
        assertTrue(Department.isValidDepartment("LoGiStIcs"));
        assertTrue(Department.isValidDepartment("Log1i5t1cs!@!"));
    }
}
