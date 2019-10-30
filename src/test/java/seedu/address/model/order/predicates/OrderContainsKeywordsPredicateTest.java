package seedu.address.model.order.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.phone.Capacity;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PhoneBuilder;

public class OrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OrderContainsKeywordsPredicate firstPredicate =
                new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        OrderContainsKeywordsPredicate secondPredicate =
                new OrderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderContainsKeywordsPredicate firstPredicateCopy =
                new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword that matches phone name
        OrderContainsKeywordsPredicate predicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("iPhone"));
        assertTrue(predicate.test(new OrderBuilder().withPhone(
                new PhoneBuilder().withName("iPhone").build()).build()));

        //one keyword that matches phone capacity
        predicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("128"));
        assertTrue(predicate.test(new OrderBuilder().withPhone(
                new PhoneBuilder().withCapacity(Capacity.SIZE_128GB).build()).build()));


        //one keyword that matches phone capacity
        predicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("Purple"));
        assertTrue(predicate.test(new OrderBuilder().withPhone(
                new PhoneBuilder().withColour("Purple").build()).build()));




        //one keyword that matches identity number
        predicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("123456"));
        assertTrue(predicate.test(new OrderBuilder().withPhone(
                new PhoneBuilder().withIdentityNumber("123456789123456").build()).build()));

        // Multiple keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("iPhone", "11"));
        assertTrue(predicate.test(new OrderBuilder().withPhone(
                new PhoneBuilder().withName("iPhone 11").build()).build()));


        // Mixed-case keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("IpHoNe", "11"));
        assertTrue(predicate.test(new OrderBuilder().withPhone(
                new PhoneBuilder().withName("iPhone 11").build()).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        // Only one matching keyword in customer name
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new OrderBuilder().withCustomer(
                new CustomerBuilder().withName("Alice Carol").build()).build()));


        // Only one matching keyword in customer contact number
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("1234", "4321"));
        assertFalse(predicate.test(new OrderBuilder().withCustomer(
                new CustomerBuilder().withContactNumber("12345678").build()).build()));

        // Zero keywords
        predicate =
                new OrderContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OrderBuilder().withPrice("$1820").build()));

        // Non-matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new OrderBuilder().withPrice("$1820").build()));

    }
}
