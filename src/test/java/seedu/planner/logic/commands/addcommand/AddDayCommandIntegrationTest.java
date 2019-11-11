package seedu.planner.logic.commands.addcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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

/**
 * Contains integration tests (interaction with the Model) for {@code AddDayCommand}.
 */
public class AddDayCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
    }

    @Test
    public void execute_newDay_success() {
        int numDays = 1;

        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(),
                model.getContacts(), model.getItinerary(), new UserPrefs());
        expectedModel.addDays(numDays);

        try {
            CommandResult commandResult = new AddDayCommand(numDays, false).execute(model);
            assertEquals(String.format(AddDayCommand.MESSAGE_SUCCESS, numDays), commandResult.getFeedbackToUser());
            assertEquals(model, expectedModel);
        } catch (CommandException e) {
            fail();
        }
    }
}
