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
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // blank status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        // invalid parts
        assertFalse(Status.isValidStatus(" peterjack@example.com")); //leading space
        // valid status
        assertTrue(Status.isValidStatus("PeterJack_1190@example.com"));
        assertTrue(Status.isValidStatus("a@bc")); // minimal
        assertTrue(Status.isValidStatus("test@localhost")); // alphabets only
        assertTrue(Status.isValidStatus("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Status.isValidStatus("123@145")); // numeric local part and domain name
        assertTrue(Status.isValidStatus("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Status.isValidStatus("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Status.isValidStatus("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
