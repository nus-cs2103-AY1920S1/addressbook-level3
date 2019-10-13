package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudyPlanBuilder;

public class StudyPlanTest {

    // TODO implement tests

    /*
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> studyPlan.getTags().remove(0));
    }

    @Test
    public void isSameStudyPlan() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudyPlan(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudyPlan(null));

        // different phone and email -> returns false
        StudyPlan editedAlice = new StudyPlanBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameStudyPlan(editedAlice));

        // different name -> returns false
        editedAlice = new StudyPlanBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudyPlan(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new StudyPlanBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameStudyPlan(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new StudyPlanBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameStudyPlan(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new StudyPlanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameStudyPlan(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        StudyPlan aliceCopy = new StudyPlanBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different studyPlan -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        StudyPlan editedAlice = new StudyPlanBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudyPlanBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudyPlanBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new StudyPlanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudyPlanBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
     */
}
