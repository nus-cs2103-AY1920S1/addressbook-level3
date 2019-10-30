//package seedu.address.model.finance.logentry;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.testutil.LogEntryBuilder;
//
//public class LogEntryMatchesAmountPredicateTest {
//
//    @Test
//    public void equals() {
//        List<String> firstPredicateKeywordList = Collections.singletonList("first");
//        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
//
//        LogEntryMatchesAmountPredicate firstPredicate =
//                new LogEntryMatchesAmountPredicate(firstPredicateKeywordList);
//        LogEntryMatchesAmountPredicate secondPredicate =
//                new LogEntryMatchesAmountPredicate(secondPredicateKeywordList);
//
//        // same object -> returns true
//        assertTrue(firstPredicate.equals(firstPredicate));
//
//        // same values -> returns true
//        LogEntryMatchesAmountPredicate firstPredicateCopy =
//                new LogEntryMatchesAmountPredicate(firstPredicateKeywordList);
//        assertTrue(firstPredicate.equals(firstPredicateCopy));
//
//        // different types -> returns false
//        assertFalse(firstPredicate.equals(1));
//
//        // null -> returns false
//        assertFalse(firstPredicate.equals(null));
//
//        // different task -> returns false
//        assertFalse(firstPredicate.equals(secondPredicate));
//    }
//
//    @Test
//    public void test_logEntryMatchesAmount_returnsTrue() {
//        // One amounts
//        LogEntryMatchesAmountPredicate predicate =
//                new LogEntryMatchesAmountPredicate(Collections.singletonList("3.30"));
//        assertTrue(predicate.test(new LogEntryBuilder()
//                .withAmount("3.3")
//                .buildSpend()));
//
//        // Multiple amounts
//        predicate = new LogEntryMatchesAmountPredicate(Arrays.asList("2", "5.6"));
//        assertTrue(predicate.test(new LogEntryBuilder()
//                .withAmount("2")
//                .buildSpend()));
//
//        // Zero amounts, no specification requirement, matches all
//        predicate = new LogEntryMatchesAmountPredicate(Collections.emptyList());
//        assertTrue(predicate.test(new LogEntryBuilder()
//                .buildSpend()));
//
//    }
//
//    @Test
//    public void test_logEntryMatchesAmount_returnsFalse() {
//        // Non-matching amounts
//        LogEntryMatchesAmountPredicate predicate =
//                new LogEntryMatchesAmountPredicate(Arrays.asList("3.99"));
//        assertFalse(predicate.test(new LogEntryBuilder()
//                .withAmount("1.1")
//                .buildBorrow()));
//
//        // Keywords match categories, transaction date, description, transaction methods,
//        // place, person (from/to) but not amount
//        predicate =
//                new LogEntryMatchesAmountPredicate(
//                        Arrays.asList("sister", "kia", "ora", "10-01-2019", "Visa", "Mastercard"));
//        assertFalse(predicate.test(new LogEntryBuilder()
//                .withAmount("300")
//                .withTransactionDate("10-01-2019")
//                .withDescription("Visa")
//                .withCats("kia", "ora")
//                .withTransactionMethod("Mastercard")
//                .withPerson("sister")
//                .buildBorrow()));
//    }
//}
