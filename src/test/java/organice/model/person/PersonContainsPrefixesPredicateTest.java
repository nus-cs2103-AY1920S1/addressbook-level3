package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import org.junit.jupiter.api.Test;

import organice.logic.commands.FindCommand;
import organice.logic.parser.ArgumentMultimap;
import organice.logic.parser.ArgumentTokenizer;
import organice.testutil.PersonBuilder;

public class PersonContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        //TODO: Replace ArgumentTokenizer with stub
        ArgumentMultimap firstPredicateKeywordList =
                ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Alice", PREFIX_NAME);
        ArgumentMultimap secondPredicateKeywordList = ArgumentTokenizer
                .tokenize("n/Alice Benson", PREFIX_NAME);

        PersonContainsPrefixesPredicate firstPredicate =
                new PersonContainsPrefixesPredicate(firstPredicateKeywordList);
        PersonContainsPrefixesPredicate secondPredicate =
                new PersonContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsPrefixesPredicate firstPredicateCopy =
                new PersonContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsPrefixes_returnsTrue() {
        // One keyword
        PersonContainsPrefixesPredicate predicate = new PersonContainsPrefixesPredicate(
                        ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Alice", PREFIX_NAME));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsPrefixesPredicate(
                ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Alice Bob", PREFIX_NAME));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsPrefixesPredicate(
                ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Bob Carol", PREFIX_NAME));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsPrefixesPredicate(
                ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/aLIce bOB", PREFIX_NAME));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords should return false
        PersonContainsPrefixesPredicate predicate = new PersonContainsPrefixesPredicate(ArgumentTokenizer
                .tokenize(FindCommand.COMMAND_WORD, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_TYPE,
                        PREFIX_AGE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE, PREFIX_TISSUE_TYPE,
                        PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsPrefixesPredicate(
                ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Carol", PREFIX_NAME));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match type, phone, nric and address, but does not match name
        predicate = new PersonContainsPrefixesPredicate(ArgumentTokenizer
                .tokenize(FindCommand.COMMAND_WORD + " n/Benson t/doctor ic/S1111111A p/12345",
                        PREFIX_NAME, PREFIX_TYPE, PREFIX_NRIC, PREFIX_PHONE));
        assertFalse(predicate.test(new PersonBuilder().withType("doctor").withNric("S1111111A").withName("Alice")
                .withPhone("12345").build()));

    }
}
