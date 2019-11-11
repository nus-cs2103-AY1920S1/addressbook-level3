package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.BENSON;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.DANIEL;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITY_A;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITY_B;
import static seedu.planner.testutil.contact.TypicalContacts.ALICE;
import static seedu.planner.testutil.contact.TypicalContacts.CARL;
import static seedu.planner.testutil.day.TypicalActivityWithTime.ACTIVITYWITHTIME_A;
import static seedu.planner.testutil.day.TypicalActivityWithTime.ACTIVITYWITHTIME_B;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.result.ResultInformation;

public class ResultInformationTest {
    @Test
    public void execute_accessors_success() {
        String description = "description";
        ResultInformation resultInformationContact = new ResultInformation(ALICE, INDEX_FIRST);
        ResultInformation resultInformationActivity = new ResultInformation(ACTIVITY_A, INDEX_FIRST);
        ResultInformation resultInformationAccommodation = new ResultInformation(BENSON, INDEX_FIRST);
        ResultInformation resultInformationActivityWithTime = new ResultInformation(ACTIVITYWITHTIME_A, INDEX_FIRST);
        ResultInformation resultInformationContactWithDescription = new ResultInformation(CARL,
                INDEX_FIRST, description);

        //field accessors
        assertTrue(resultInformationContact.getContact().get().equals(ALICE));
        assertTrue(resultInformationActivity.getActivity().get().equals(ACTIVITY_A));
        assertTrue(resultInformationAccommodation.getAccommodation().get().equals(BENSON));
        assertTrue(resultInformationActivityWithTime.getActivityWithTime().get().equals(ACTIVITYWITHTIME_A));
        assertTrue(resultInformationContactWithDescription.getDescription().get().equals(description));
        assertTrue(resultInformationActivity.getIndex().equals(INDEX_FIRST));

        assertFalse(resultInformationContact.getContact().get().equals(CARL));
        assertFalse(resultInformationActivity.getActivity().get().equals(ACTIVITY_B));
        assertFalse(resultInformationAccommodation.getAccommodation().get().equals(DANIEL));
        assertFalse(resultInformationActivityWithTime.getActivityWithTime().get().equals(ACTIVITYWITHTIME_B));
        assertFalse(resultInformationContactWithDescription.getDescription().get().equals("wrong"));
        assertFalse(resultInformationActivity.getIndex().equals(INDEX_SECOND));
    }

    @Test
    public void equals() {
        ResultInformation resultInformationContact = new ResultInformation(ALICE, INDEX_FIRST);
        ResultInformation resultInformationActivity = new ResultInformation(ACTIVITY_A, INDEX_FIRST);
        ResultInformation resultInformationAccommodation = new ResultInformation(BENSON, INDEX_FIRST);
        ResultInformation resultInformationActivityWithTime = new ResultInformation(ACTIVITYWITHTIME_A, INDEX_FIRST);

        ResultInformation resultInformationContactWithDescription = new ResultInformation(CARL,
                INDEX_FIRST, "description");
        ResultInformation resultInformationActivityWithDescription = new ResultInformation(ACTIVITY_B,
                INDEX_FIRST, "description");
        ResultInformation resultInformationAccommodationWithDescription = new ResultInformation(DANIEL,
                INDEX_FIRST, "description");

        // same values -> returns true
        assertTrue(resultInformationContact.equals(new ResultInformation(ALICE, INDEX_FIRST)));
        assertTrue(resultInformationActivity.equals(new ResultInformation(ACTIVITY_A, INDEX_FIRST)));
        assertTrue(resultInformationAccommodation.equals(new ResultInformation(BENSON, INDEX_FIRST)));
        assertTrue(resultInformationActivityWithTime.equals(new ResultInformation(ACTIVITYWITHTIME_A, INDEX_FIRST)));
        assertTrue(resultInformationContactWithDescription.equals(new ResultInformation(CARL,
                INDEX_FIRST, "description")));
        assertTrue(resultInformationActivityWithDescription.equals(new ResultInformation(ACTIVITY_B,
                INDEX_FIRST, "description")));
        assertTrue(resultInformationAccommodationWithDescription.equals(new ResultInformation(DANIEL,
                INDEX_FIRST, "description")));

        // same object -> returns true
        assertTrue(resultInformationContact.equals(resultInformationContact));

        // null -> returns false
        assertFalse(resultInformationActivity.equals(null));

        // different types -> returns false
        assertFalse(resultInformationAccommodation.equals(0.5f));

        // different Contact value -> returns false
        assertFalse(resultInformationContact.equals(new ResultInformation(CARL, INDEX_FIRST)));

        // different Activity value -> returns false
        assertFalse(resultInformationActivity.equals(new ResultInformation(ACTIVITY_B, INDEX_FIRST)));

        // different Accommodation value -> returns false
        assertFalse(resultInformationAccommodation.equals(new ResultInformation(DANIEL, INDEX_FIRST)));

        // different ActivityWithTime value -> returns false
        assertFalse(resultInformationActivityWithTime.equals(new ResultInformation(ACTIVITYWITHTIME_B, INDEX_FIRST)));

        // different description value -> returns false
        assertFalse(resultInformationContactWithDescription.equals(new ResultInformation(CARL,
                INDEX_FIRST, "wrong")));

        // different Index value -> returns false
        assertFalse(resultInformationActivity.equals(new ResultInformation(ACTIVITY_A, INDEX_SECOND)));
    }
}
