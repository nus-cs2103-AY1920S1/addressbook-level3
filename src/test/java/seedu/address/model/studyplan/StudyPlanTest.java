package seedu.address.model.studyplan;

/*
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
*/


/**
 * A test class for {@code StudyPlan}.
 */
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
        assertTrue(SP_1.isSameStudyPlan(SP_1));

        // null -> returns false
        assertFalse(SP_1.isSameStudyPlan(null));


        StudyPlan editedSP1 = new StudyPlanBuilder(SP_1).withTitle("Different title").build();


        // different phone and email -> returns false
        assertFalse(SP_1.isSameStudyPlan(editedSP1));

        // different name -> returns false
        editedSP1 = new StudyPlanBuilder(SP_1).withName(VALID_NAME_BOB).build();
        assertFalse(SP_1.isSameStudyPlan(editedSP1));


        // same index, different attributes -> returns true
        assertTrue(SP_1.isSameStudyPlan(editedSP1));

        // same index, same title, different attributes -> returns true
        editedSP1 = new StudyPlanBuilder(SP_1).withTitle("Different title").withActivated(false).build();
        assertTrue(SP_1.isSameStudyPlan(editedSP1));


        // same name, same phone, same email, different attributes -> returns true
        editedSP1 = new StudyPlanBuilder(SP_1).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(SP_1.isSameStudyPlan(editedSP1));

    }

    @Test
    public void equals() {
        // same values -> returns true
        StudyPlan sp1Copy = new StudyPlanBuilder(SP_1).build();
        assertTrue(SP_1.equals(sp1Copy));

        // same object -> returns true
        assertTrue(SP_1.equals(SP_1));

        // null -> returns false
        assertFalse(SP_1.equals(null));

        // different type -> returns false
        assertFalse(SP_1.equals(5));

        // different studyPlan -> returns false
        assertFalse(SP_1.equals(SP_2));

        // different index -> returns false
        StudyPlan editedSP1 = new StudyPlanBuilder(SP_1).withIndex(6).build();
        assertFalse(SP_1.equals(editedSP1));


        // different phone -> returns false
        editedSP1 = new StudyPlanBuilder(SP_1).withPhone(VALID_PHONE_BOB).build();
        assertFalse(SP_1.equals(editedSP1));

        // different email -> returns false
        editedSP1 = new StudyPlanBuilder(SP_1).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(SP_1.equals(editedSP1));

        // different address -> returns false
        editedSP1 = new StudyPlanBuilder(SP_1).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(SP_1.equals(editedSP1));

        // different tags -> returns false
        editedSP1 = new StudyPlanBuilder(SP_1).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(SP_1.equals(editedSP1));

    }
    */
}
