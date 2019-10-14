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
    public void isValidTopic() {
        // null topic number
        assertThrows(NullPointerException.class, () -> Topic.isValidTopic(null));

        // invalid topic numbers
        assertFalse(Topic.isValidTopic("")); // empty string
        assertFalse(Topic.isValidTopic(" ")); // spaces only

        // valid topic numbers
        assertTrue(Topic.isValidTopic("911")); // exactly 3 numbers
        assertTrue(Topic.isValidTopic("93121534"));
        assertTrue(Topic.isValidTopic("124293842033123")); // long topic numbers
    }
}
