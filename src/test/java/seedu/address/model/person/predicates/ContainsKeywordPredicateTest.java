package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordPredicateTest {

    @Test
    public void equals() {
        PersonContainsKeywordPredicate firstPredicate = new PersonContainsKeywordPredicate("first");
        PersonContainsKeywordPredicate secondPredicate = new PersonContainsKeywordPredicate("second");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordPredicate firstPredicateCopy = new PersonContainsKeywordPredicate("first");
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
        Person testPerson = new PersonBuilder().withPatientId("12345678A")
            .withName("Alice").withPhone("94590789").build();

        // One keyword
        PersonContainsKeywordPredicate predicate = new PersonContainsKeywordPredicate("456");
        assertTrue(predicate.test(testPerson));

        // keyword matches multiple possible fields
        predicate = new PersonContainsKeywordPredicate("459");
        assertTrue(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("9");
        assertTrue(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("A");
        assertTrue(predicate.test(testPerson));

        // keyword matches only one field
        predicate = new PersonContainsKeywordPredicate("789");
        assertTrue(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("8A");
        assertTrue(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("Al");
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_idContainsKeywords_returnsFalse() {
        Person testPerson = new PersonBuilder().withPatientId("123456789")
            .withName("Alice").withPhone(VALID_PHONE_AMY).build();

        // Zero keywords
        PersonContainsKeywordPredicate predicate = new PersonContainsKeywordPredicate("");
        assertTrue(predicate.test(testPerson));

        // Non-matching keyword
        predicate = new PersonContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("45");
        assertTrue(predicate.test(testPerson));

        // Keywords match email and address, but does not match id, name or phone
        testPerson = new PersonBuilder().withPatientId(AMY.getReferenceId().toString())
                .withName("Alice").withPhone(VALID_PHONE_AMY)
                .withEmail("alice@email.com").withAddress("Main Street").build();
        predicate = new PersonContainsKeywordPredicate("+14598A");
        assertFalse(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("alice@email.com");
        assertFalse(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("Main");
        assertFalse(predicate.test(testPerson));

        predicate = new PersonContainsKeywordPredicate("Street");
        assertFalse(predicate.test(testPerson));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordPredicate predicate = new PersonContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonContainsKeywordPredicate("Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordPredicate("Bob");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        predicate = new PersonContainsKeywordPredicate("Carol");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordPredicate("aLIce");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonContainsKeywordPredicate("bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordPredicate predicate = new PersonContainsKeywordPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new PersonContainsKeywordPredicate("1245");
        assertFalse(predicate.test(AMY));

        predicate = new PersonContainsKeywordPredicate("alice@email.com");
        assertFalse(predicate.test(AMY));

        predicate = new PersonContainsKeywordPredicate("Main");
        assertFalse(predicate.test(AMY));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordPredicate predicate = new PersonContainsKeywordPredicate("2345");
        assertTrue(predicate.test(new PersonBuilder().withPhone("92345678").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordPredicate("1234");
        assertTrue(predicate.test(new PersonBuilder().withPhone("92345678").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordPredicate("4567");
        assertTrue(predicate.test(new PersonBuilder().withPhone("92345678").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordPredicate predicate = new PersonContainsKeywordPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withPhone("92345678").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("92345678").build()));

        predicate = new PersonContainsKeywordPredicate("123");
        assertTrue(predicate.test(new PersonBuilder().withPatientId("123456789")
                .withName("Alice").withPhone("92345678").build()));

        // Keywords match email and address, but does not match id, name or phone
        predicate = new PersonContainsKeywordPredicate("12-45");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("92345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        predicate = new PersonContainsKeywordPredicate("alice@email.com");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("92345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

}
