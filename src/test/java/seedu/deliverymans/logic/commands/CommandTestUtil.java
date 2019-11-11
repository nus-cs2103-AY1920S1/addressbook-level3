package seedu.deliverymans.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.deliverymans.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.restaurant.NameContainsKeywordsPredicate;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ONE = "Name One";
    public static final String VALID_NAME_TWO = "Name Two";
    public static final String VALID_LOCATION_ONE = "Jurong";
    public static final String VALID_LOCATION_TWO = "Changi";
    public static final String VALID_TAG_ONE = "Tag1";
    public static final String VALID_TAG_TWO = "Tag2";

    public static final String NAME_DESC_ONE = " " + PREFIX_NAME + VALID_NAME_ONE;
    public static final String NAME_DESC_TWO = " " + PREFIX_NAME + VALID_NAME_TWO;
    public static final String LOCATION_DESC_ONE = " " + PREFIX_LOCATION + VALID_LOCATION_ONE;
    public static final String LOCATION_DESC_TWO = " " + PREFIX_LOCATION + VALID_LOCATION_TWO;
    public static final String TAG_DESC_ONE = " " + PREFIX_TAG + VALID_TAG_ONE;
    public static final String TAG_DESC_TWO = " " + PREFIX_TAG + VALID_TAG_TWO;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "N@me";
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "Malaysia";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "T@g";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the restaurant database, filtered restaurant list and restaurant in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        RestaurantDatabase expectedRestaurantDatabase = new RestaurantDatabase(actualModel.getRestaurantDatabase());
        List<Restaurant> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRestaurantList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedRestaurantDatabase, actualModel.getRestaurantDatabase());
        assertEquals(expectedFilteredList, actualModel.getFilteredRestaurantList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the restaurant at the given {@code targetIndex} in the
     * {@code model}'s restaurant database.
     */
    public static void showRestaurantAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRestaurantList().size());

        Restaurant restaurant = model.getFilteredRestaurantList().get(targetIndex.getZeroBased());
        final String[] splitName = restaurant.getName().fullName.split("\\s+");
        model.updateFilteredRestaurantList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRestaurantList().size());
    }
}
