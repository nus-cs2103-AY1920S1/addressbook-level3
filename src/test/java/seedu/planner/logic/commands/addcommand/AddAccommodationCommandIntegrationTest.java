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
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.testutil.accommodation.AccommodationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddAccommodationCommand}.
 */
public class AddAccommodationCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
    }

    @Test
    public void execute_newAccommodation_success() {
        Accommodation validAccommodation = new AccommodationBuilder().build();

        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), new UserPrefs());
        expectedModel.addAccommodation(validAccommodation);

        try {
            CommandResult commandResult = new AddAccommodationCommand(validAccommodation, false).execute(model);
            assertEquals(String.format(AddAccommodationCommand.MESSAGE_SUCCESS, validAccommodation),
                    commandResult.getFeedbackToUser());
            assertEquals(model, expectedModel);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_duplicateAccommodation_throwsCommandException() {
        Accommodation accommodationInList = model.getAccommodations().getAccommodationList().get(0);
        assertCommandFailure(new AddAccommodationCommand(accommodationInList, false), model,
                AddAccommodationCommand.MESSAGE_DUPLICATE_ACCOMMODATION);
    }

}
