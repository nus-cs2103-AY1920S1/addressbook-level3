package com.dukeacademy.model.question;

import static com.dukeacademy.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidEmail(null));

        // blank status
        assertFalse(Status.isValidEmail("")); // empty string
        assertFalse(Status.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Status.isValidEmail("@example.com")); // missing local part
        assertFalse(Status.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Status.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Status.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Status.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Status.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Status.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Status.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Status.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Status.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Status.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Status.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Status.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Status.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Status.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Status.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid status
        assertTrue(Status.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Status.isValidEmail("a@bc")); // minimal
        assertTrue(Status.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Status.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Status.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Status.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Status.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Status.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
