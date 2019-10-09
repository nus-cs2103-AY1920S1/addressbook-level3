package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate(firstPredicateKeywordList);
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_idContainsKeywords_returnsTrue() {
        Person testPerson = new PersonBuilder().withId("14598A").withName("Alice").withPhone("12345").build();

        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("459"));
        assertTrue(predicate.test(testPerson));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("459", "98A"));
        assertTrue(predicate.test(testPerson));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("459", "124"));
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_idContainsKeywords_returnsFalse() {
        Person testPerson = new PersonBuilder().withId("14598A").withName("Alice").withPhone("12345").build();

        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(testPerson));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("23"));
        assertTrue(predicate.test(testPerson));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new ContainsKeywordsPredicate(Arrays.asList("+14598A", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new ContainsKeywordsPredicate(Arrays.asList("1245", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("2345"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("2345", "1234"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("2345", "4567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("123"));
        assertTrue(predicate.test(new PersonBuilder().withId("98A").withName("Alice").withPhone("12345").build()));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new ContainsKeywordsPredicate(Arrays.asList("12-45", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

}
