package seedu.planner.logic.commands;

import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
import static seedu.planner.testutil.activity.TypicalActivity.getTypicalActivityManager;
import static seedu.planner.testutil.contact.TypicalContacts.getTypicalContactManager;
import static seedu.planner.testutil.day.TypicalDays.getTypicalItinerary;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.AccommodationManager;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyPlanner_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS,
                new UiFocus[]{UiFocus.AGENDA}, expectedModel);
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

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS,
                new UiFocus[]{UiFocus.AGENDA}, expectedModel);
    }

}


