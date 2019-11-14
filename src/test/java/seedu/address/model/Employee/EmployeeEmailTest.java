package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmployeeEmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmployeeEmail(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new EmployeeEmail(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> EmployeeEmail.isValidEmail(null));

        // blank email
        assertFalse(EmployeeEmail.isValidEmail("")); // empty string
        assertFalse(EmployeeEmail.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(EmployeeEmail.isValidEmail("@example.com")); // missing local part
        assertFalse(EmployeeEmail.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(EmployeeEmail.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EmployeeEmail.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(EmployeeEmail.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(EmployeeEmail.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(EmployeeEmail.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(EmployeeEmail.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(EmployeeEmail.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(EmployeeEmail.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(EmployeeEmail.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(EmployeeEmail.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(EmployeeEmail.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(EmployeeEmail.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(EmployeeEmail.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(EmployeeEmail.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(EmployeeEmail.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(EmployeeEmail.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(EmployeeEmail.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(EmployeeEmail.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
