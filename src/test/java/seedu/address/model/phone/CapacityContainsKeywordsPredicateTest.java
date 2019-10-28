package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.phone.predicates.CapacityContainsKeywordsPredicate;
import seedu.address.testutil.PhoneBuilder;

public class CapacityContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1024");
        List<String> secondPredicateKeywordList = Arrays.asList("8", "16");


        CapacityContainsKeywordsPredicate firstPred =
                new CapacityContainsKeywordsPredicate(firstPredicateKeywordList);
        CapacityContainsKeywordsPredicate firstPredCopy =
                new CapacityContainsKeywordsPredicate(firstPredicateKeywordList);
        CapacityContainsKeywordsPredicate secondPred =
                new CapacityContainsKeywordsPredicate(secondPredicateKeywordList);

        //same obj
        assertTrue(firstPred.equals(firstPredCopy));

        //same value
        assertEquals(firstPred, firstPred);

        // different types -> returns false
        assertNotEquals(1, firstPred);

        // null -> returns false
        assertNotEquals(null, firstPred);

        // different phone -> returns false
        assertNotEquals(firstPred, secondPred);
    }


    @Test
    public void test_capacityContainsKeywords_returnsTrue() {
        // One keyword
        CapacityContainsKeywordsPredicate predicate1 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("8"));
        CapacityContainsKeywordsPredicate predicate2 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("16"));
        CapacityContainsKeywordsPredicate predicate3 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("32"));
        CapacityContainsKeywordsPredicate predicate4 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("64"));
        CapacityContainsKeywordsPredicate predicate5 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("128"));
        CapacityContainsKeywordsPredicate predicate6 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("256"));
        CapacityContainsKeywordsPredicate predicate7 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("512"));
        CapacityContainsKeywordsPredicate predicate8 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("1024"));

        assertTrue(predicate1.test(new PhoneBuilder().withCapacity(Capacity.SIZE_8GB).build()));
        assertTrue(predicate2.test(new PhoneBuilder().withCapacity(Capacity.SIZE_16GB).build()));
        assertTrue(predicate3.test(new PhoneBuilder().withCapacity(Capacity.SIZE_32GB).build()));
        assertTrue(predicate4.test(new PhoneBuilder().withCapacity(Capacity.SIZE_64GB).build()));
        assertTrue(predicate5.test(new PhoneBuilder().withCapacity(Capacity.SIZE_128GB).build()));
        assertTrue(predicate6.test(new PhoneBuilder().withCapacity(Capacity.SIZE_256GB).build()));
        assertTrue(predicate7.test(new PhoneBuilder().withCapacity(Capacity.SIZE_512GB).build()));
        assertTrue(predicate8.test(new PhoneBuilder().withCapacity(Capacity.SIZE_1024GB).build()));

    }
    @Test
    public void test_capacityDoesNotContainKeywords_returnsFalse() {
        CapacityContainsKeywordsPredicate predicate =
                new CapacityContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PhoneBuilder().withCapacity(Capacity.SIZE_8GB).build()));

        //non matching
        CapacityContainsKeywordsPredicate predicate2 =
                new CapacityContainsKeywordsPredicate(Collections.singletonList("121313131"));
        assertFalse(predicate2.test(new PhoneBuilder().withCapacity(Capacity.SIZE_8GB).build()));
    }
}
