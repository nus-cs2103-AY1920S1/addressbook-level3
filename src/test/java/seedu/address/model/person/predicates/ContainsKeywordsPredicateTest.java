package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate("first");
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate("second");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate("first");
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
        Person testPerson = new PersonBuilder().withPatientId("14598A").withName("Alice").withPhone("12345").build();

        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate("459");
        assertTrue(predicate.test(testPerson));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate("459");
        assertTrue(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate("98A");
        assertTrue(predicate.test(testPerson));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate("459");
        assertTrue(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate("124");
        assertFalse(predicate.test(testPerson));
    }

    @Test
    public void test_idContainsKeywords_returnsFalse() {
        Person testPerson = new PersonBuilder().withPatientId("14598A").withName("Alice").withPhone("12345").build();

        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate("");
        assertTrue(predicate.test(testPerson));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate("23");
        assertTrue(predicate.test(testPerson));

        // Keywords match email and address, but does not match id, name or phone
        testPerson = new PersonBuilder().withPatientId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build();
        predicate = new ContainsKeywordsPredicate("+14598A");
        assertFalse(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate("alice@email.com");
        assertFalse(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate("Main");
        assertFalse(predicate.test(testPerson));

        predicate = new ContainsKeywordsPredicate("Street");
        assertFalse(predicate.test(testPerson));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate("Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate("Bob");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        predicate = new ContainsKeywordsPredicate("Carol");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate("aLIce");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate("bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new ContainsKeywordsPredicate("1245");
        assertFalse(predicate.test(new PersonBuilder().withPatientId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        predicate = new ContainsKeywordsPredicate("alice@email.com");
        assertFalse(predicate.test(new PersonBuilder().withPatientId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        predicate = new ContainsKeywordsPredicate("Main");
        assertFalse(predicate.test(new PersonBuilder().withPatientId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate("2345");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate("1234");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate("4567");
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        predicate = new ContainsKeywordsPredicate("123");
        assertTrue(predicate.test(new PersonBuilder().withPatientId("98A")
                .withName("Alice").withPhone("12345").build()));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new ContainsKeywordsPredicate("12-45");
        assertFalse(predicate.test(new PersonBuilder().withPatientId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        predicate = new ContainsKeywordsPredicate("alice@email.com");
        assertFalse(predicate.test(new PersonBuilder().withPatientId("98A").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

}
