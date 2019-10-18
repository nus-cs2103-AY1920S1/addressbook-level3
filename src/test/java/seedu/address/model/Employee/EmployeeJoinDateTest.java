package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import seedu.address.model.employee.EmployeeJoinDate;

class EmployeeJoinDateTest {

    @Test
    void employeeJoinDateToString() {
        assertEquals(new EmployeeJoinDate(LocalDate.of(2019, 10, 20)).toString(),
                "20/10/2019");
    }

    @Test
    void employeeJoinDateEquals() {
        assertEquals(new EmployeeJoinDate(LocalDate.of(2019, 10, 20)),
                new EmployeeJoinDate(LocalDate.of(2019, 10, 20)));

        assertNotEquals(new EmployeeJoinDate(LocalDate.of(2019, 10, 20)),
                new EmployeeJoinDate(LocalDate.of(2019, 10, 19))); //diff day

        assertNotEquals(new EmployeeJoinDate(LocalDate.of(2019, 10, 20)),
                new EmployeeJoinDate(LocalDate.of(2019, 9, 19))); //diff month

        assertNotEquals(new EmployeeJoinDate(LocalDate.of(2019, 10, 20)),
                new EmployeeJoinDate(LocalDate.of(2020, 10, 19))); //diff year
    }

    @Test
    void isValidJoinDate() {
        //null event venue
        assertThrows(NullPointerException.class, () -> EmployeeJoinDate.isValidJoinDate(null));

        // invalid join date format
        assertFalse(EmployeeJoinDate.isValidJoinDate("")); // empty string
        assertFalse(EmployeeJoinDate.isValidJoinDate(" ")); // spaces only
        assertFalse(EmployeeJoinDate.isValidJoinDate("2019/12/21")); // invalid date format
        assertFalse(EmployeeJoinDate.isValidJoinDate("12-02-2019")); // invalid date format
        assertFalse(EmployeeJoinDate.isValidJoinDate("12 Aug 2019")); // invalid date format


        // valid join date format
        assertTrue(EmployeeJoinDate.isValidJoinDate("02/12/2019")); // dd/MM/yyyy format
        assertTrue(EmployeeJoinDate.isValidJoinDate("02/01/2018")); // dd/MM/yyyy format
    }
}
