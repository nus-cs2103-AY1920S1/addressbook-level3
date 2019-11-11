package seedu.planner.logic.commands.addcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
import static seedu.planner.testutil.activity.TypicalActivity.getTypicalActivityManager;
import static seedu.planner.testutil.contact.TypicalContacts.getTypicalContactManager;
import static seedu.planner.testutil.day.TypicalDays.getTypicalItinerary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.activity.Activity;
import seedu.planner.testutil.activity.ActivityBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddActivityCommand}.
 */
public class AddActivityCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
    }

    @Test
    public void execute_newActivity_success() {
        Activity validActivity = new ActivityBuilder().build();

        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), new UserPrefs());
        expectedModel.addActivity(validActivity);

        try {
            CommandResult commandResult = new AddActivityCommand(validActivity, false).execute(model);
            assertEquals(String.format(AddActivityCommand.MESSAGE_SUCCESS, validActivity),
                    commandResult.getFeedbackToUser());
            assertEquals(model, expectedModel);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_duplicateActivity_throwsCommandException() {
        Activity activityInList = model.getActivities().getActivityList().get(0);
        assertCommandFailure(new AddActivityCommand(activityInList, false), model,
                AddActivityCommand.MESSAGE_DUPLICATE_ACTIVITY);
    }
}
