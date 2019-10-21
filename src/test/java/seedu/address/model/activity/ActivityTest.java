package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TypicalActivities;
import seedu.address.testutil.TypicalPersons;

public class ActivityTest {

    @Test
    public void getParticipantIds() {
        Activity lunch = TypicalActivities.LUNCH;
        ArrayList<Integer> expectedIds = new ArrayList<Integer>();
        expectedIds.add(TypicalPersons.BENSON.getPrimaryKey());
        expectedIds.add(TypicalPersons.CARL.getPrimaryKey());

        assertEquals(lunch.getParticipantIds(), expectedIds);
    }

    @Test
    public void hasPerson() {
        Activity lunch = TypicalActivities.LUNCH;

        // Person exists -> Return true
        assertTrue(lunch.hasPerson(TypicalPersons.BENSON.getPrimaryKey()));

        // Person doesn't exist -> return false
        assertFalse(lunch.hasPerson(TypicalPersons.ALICE.getPrimaryKey()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Activity lunch = TypicalActivities.LUNCH;
        Activity lunchCopy = new ActivityBuilder(lunch).build();
        assertTrue(lunch.equals(lunchCopy));

        // same object -> returns true
        assertTrue(lunch.equals(lunch));

        // null -> returns false
        assertFalse(lunch.equals(null));

        // different type -> returns false
        assertFalse(lunch.equals(5));

        // different activity -> returns false
        assertFalse(lunch.equals(TypicalActivities.BREAKFAST));

        // different title -> returns false
        Activity editedLunch = new ActivityBuilder(lunch).withTitle("Better Lunch").build();
        assertFalse(lunch.equals(editedLunch));

        // different participants -> returns false
        editedLunch = new ActivityBuilder(lunch).addPerson(TypicalPersons.ALICE).build();
        assertFalse(lunch.equals(editedLunch));

        //TODO: Different expenses -> returns false;
    }

}
