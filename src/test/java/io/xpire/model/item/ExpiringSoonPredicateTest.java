package io.xpire.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.xpire.commons.util.DateUtil;
import io.xpire.testutil.ItemBuilder;

public class ExpiringSoonPredicateTest {

    public static final String DATE_FORMAT = "d/M/yyyy";

    @Test
    public void equals() {

        int daysToExpiryDate1 = 0;
        int daysToExpiryDate2 = 10;

        ExpiringSoonPredicate firstPredicate = new ExpiringSoonPredicate(daysToExpiryDate1);
        ExpiringSoonPredicate secondPredicate = new ExpiringSoonPredicate(daysToExpiryDate2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExpiringSoonPredicate firstPredicateCopy = new ExpiringSoonPredicate(daysToExpiryDate1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameExpiringSoon_returnsTrue() {

        LocalDate currentDate = LocalDate.now();
        ExpiringSoonPredicate predicate = new ExpiringSoonPredicate(10);

        // Within the days
        String expiryDate1 = DateUtil.convertDateToString(currentDate.plusDays(5), DATE_FORMAT);
        assertTrue(predicate.test(new ItemBuilder().withExpiryDate(expiryDate1).build()));

        // On the day
        String expiryDate2 = DateUtil.convertDateToString(currentDate.plusDays(10), DATE_FORMAT);
        assertTrue(predicate.test(new ItemBuilder().withExpiryDate(expiryDate2).build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        LocalDate currentDate = LocalDate.now();
        ExpiringSoonPredicate predicate = new ExpiringSoonPredicate(10);

        // Not within the days
        String expiryDate = DateUtil.convertDateToString(currentDate.plusDays(20), DATE_FORMAT);
        assertFalse(predicate.test(new ItemBuilder().withExpiryDate(expiryDate).build()));
    }
}
