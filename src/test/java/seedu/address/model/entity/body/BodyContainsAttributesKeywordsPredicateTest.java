package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;

// @@author dalisc

public class BodyContainsAttributesKeywordsPredicateTest {

    @Test
    public void equals() {
        String bodyArgs1 = "-b /sex female /nric S1234567A";
        ArgumentMultimap bodyMap1 = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        ArgumentMultimap bodyMap1copy = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        String bodyArgs2 = "-b /sex female /nric S1834567A /cod natural";
        ArgumentMultimap bodyMap2 = ArgumentTokenizer.tokenize(bodyArgs2, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        BodyContainsAttributesKeywordsPredicate firstPredicate =
                new BodyContainsAttributesKeywordsPredicate(bodyMap1);
        BodyContainsAttributesKeywordsPredicate secondPredicate =
                new BodyContainsAttributesKeywordsPredicate(bodyMap2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BodyContainsAttributesKeywordsPredicate firstPredicateCopy =
                new BodyContainsAttributesKeywordsPredicate(bodyMap1copy);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    /*
    @Test
    public void test_attributesContainsKeywords_returnsTrue() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        // One attribute
        String bodyArgs1 = "-b /sex female";
        ArgumentMultimap bodyMap1 = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        BodyContainsAttributesKeywordsPredicate predicate =
                new BodyContainsAttributesKeywordsPredicate(bodyMap1);
        assertTrue(predicate.test(new BodyBuilder(ALICE).build(1)));

        // Multiple attributes
        UniqueIdentificationNumberMaps.clearAllEntries();

        String bodyArgs2 = "-b /sex female /cod Stroke";
        ArgumentMultimap bodyMap2 = ArgumentTokenizer.tokenize(bodyArgs2, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        predicate = new BodyContainsAttributesKeywordsPredicate(bodyMap2);
        assertTrue(predicate.test(new BodyBuilder(ALICE).build(1)));
    }

     */

    @Test
    public void test_attributesDoesNotContainKeywords_returnsFalse() {
        // Zero attributes
        String bodyArgs = "-b /sex male /religion Buddhism";
        ArgumentMultimap bodyMap = ArgumentTokenizer.tokenize(bodyArgs, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        BodyContainsAttributesKeywordsPredicate predicate =
                new BodyContainsAttributesKeywordsPredicate(bodyMap);
        assertFalse(predicate.test(ALICE));

        // Only one matching attribute
        String bodyArgs3 = "-b /sex female /cod natural";
        ArgumentMultimap bodyMap3 = ArgumentTokenizer.tokenize(bodyArgs3, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        predicate = new BodyContainsAttributesKeywordsPredicate(bodyMap3);
        assertFalse(predicate.test(ALICE));
    }
}
