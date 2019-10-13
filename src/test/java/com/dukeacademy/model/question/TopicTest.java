package com.dukeacademy.model.question;

import static com.dukeacademy.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TopicTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Topic(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Topic(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null topic number
        assertThrows(NullPointerException.class, () -> Topic.isValidPhone(null));

        // invalid topic numbers
        assertFalse(Topic.isValidPhone("")); // empty string
        assertFalse(Topic.isValidPhone(" ")); // spaces only
        assertFalse(Topic.isValidPhone("91")); // less than 3 numbers
        assertFalse(Topic.isValidPhone("topic")); // non-numeric
        assertFalse(Topic.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Topic.isValidPhone("9312 1534")); // spaces within digits

        // valid topic numbers
        assertTrue(Topic.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Topic.isValidPhone("93121534"));
        assertTrue(Topic.isValidPhone("124293842033123")); // long topic numbers
    }
}
