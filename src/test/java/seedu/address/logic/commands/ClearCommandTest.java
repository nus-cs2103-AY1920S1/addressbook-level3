package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
import static seedu.address.testutil.activity.TypicalActivity.getTypicalActivityManager;
import static seedu.address.testutil.contact.TypicalContacts.getTypicalContactManager;
import static seedu.address.testutil.day.TypicalDays.getTypicalItinerary;

import org.junit.jupiter.api.Test;

import seedu.address.model.AccommodationManager;
import seedu.address.model.ActivityManager;
import seedu.address.model.ContactManager;
import seedu.address.model.Itinerary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyPlanner_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPlanner_success() {
        Model model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());

        expectedModel.setAccommodations(new AccommodationManager());
        expectedModel.setActivities(new ActivityManager());
        expectedModel.setContacts(new ContactManager());
        expectedModel.setItinerary(new Itinerary());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}


