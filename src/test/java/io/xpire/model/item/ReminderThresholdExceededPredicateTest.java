package io.xpire.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.xpire.commons.util.DateUtil;
import io.xpire.testutil.XpireItemBuilder;

public class ReminderThresholdExceededPredicateTest {

    public static final String DATE_FORMAT = "d/M/yyyy";

    @Test
    public void equals() {

        ReminderThresholdExceededPredicate predicate = new ReminderThresholdExceededPredicate();

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

    }

    @Test
    public void test_reminderThresholdExceeded_returnsTrue() {


        ReminderThresholdExceededPredicate predicate = new ReminderThresholdExceededPredicate();
        LocalDate currentDate = LocalDate.now();

        // on the day of reminder
        String expiryDate1 = DateUtil.convertDateToString(currentDate.plusDays(1));
        assertTrue(predicate.test(new XpireItemBuilder().withExpiryDate(expiryDate1)
                .withReminderThreshold("1").build()));

        // after the day of reminder
        String expiryDate2 = DateUtil.convertDateToString(currentDate.plusDays(1));
        assertTrue(predicate.test(new XpireItemBuilder().withExpiryDate(expiryDate2)
                .withReminderThreshold("2").build()));

        // already expired
        String expiryDate3 = DateUtil.convertDateToString(currentDate.minusDays(1));
        assertTrue(predicate.test(new XpireItemBuilder()
                .withExpiryDate(expiryDate3).build()));
    }

    @Test
    public void test_reminderThresholdNotExceeded_returnsFalse() {

        ReminderThresholdExceededPredicate predicate = new ReminderThresholdExceededPredicate();
        LocalDate currentDate = LocalDate.now();
        String expiryDate = DateUtil.convertDateToString(currentDate.plusDays(2));
        assertFalse(predicate.test(new XpireItemBuilder().withExpiryDate(expiryDate)
                .withReminderThreshold("1").build()));
    }
}
