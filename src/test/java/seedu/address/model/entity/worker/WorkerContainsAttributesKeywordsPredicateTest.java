package seedu.address.model.entity.worker;

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
import static seedu.address.testutil.TypicalWorkers.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;

// @@author dalisc

public class WorkerContainsAttributesKeywordsPredicateTest {

    @Test
    public void equals() {
        String workerArgs1 = "-w /sex female /phoneNo 80080080";
        ArgumentMultimap workerMap1 = ArgumentTokenizer.tokenize(workerArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        ArgumentMultimap workerMap1copy = ArgumentTokenizer.tokenize(workerArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);


        String workerArgs2 = "-w /sex female /phoneNo 80090080 /dateJoined 12/12/2012";
        ArgumentMultimap workerMap2 = ArgumentTokenizer.tokenize(workerArgs2, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        WorkerContainsAttributesKeywordsPredicate firstPredicate =
                new WorkerContainsAttributesKeywordsPredicate(workerMap1);
        WorkerContainsAttributesKeywordsPredicate secondPredicate =
                new WorkerContainsAttributesKeywordsPredicate(workerMap2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        WorkerContainsAttributesKeywordsPredicate firstPredicateCopy =
                new WorkerContainsAttributesKeywordsPredicate(workerMap1copy);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_attributesContainsKeywords_returnsTrue() {
        // One attribute
        String workerArgs1 = "-w /sex female";
        ArgumentMultimap workerMap1 = ArgumentTokenizer.tokenize(workerArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        WorkerContainsAttributesKeywordsPredicate predicate =
                new WorkerContainsAttributesKeywordsPredicate(workerMap1);
        assertTrue(predicate.test(ALICE));

        // Multiple attributes
        String workerArgs2 = "-w /sex female /phoneNo 94351253";
        ArgumentMultimap workerMap2 = ArgumentTokenizer.tokenize(workerArgs2, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        predicate = new WorkerContainsAttributesKeywordsPredicate(workerMap2);
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_attributesDoesNotContainKeywords_returnsFalse() {
        // Zero attributes
        String workerArgs = "-w /sex male /phoneNo 90000253";
        ArgumentMultimap workerMap = ArgumentTokenizer.tokenize(workerArgs, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        WorkerContainsAttributesKeywordsPredicate predicate =
                new WorkerContainsAttributesKeywordsPredicate(workerMap);
        assertFalse(predicate.test(ALICE));

        // Only one matching attribute
        String workerArgs3 = "-w /sex female /employmentStatus trainee";
        ArgumentMultimap workerMap3 = ArgumentTokenizer.tokenize(workerArgs3, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        predicate = new WorkerContainsAttributesKeywordsPredicate(workerMap3);
        assertFalse(predicate.test(ALICE));
    }
}
