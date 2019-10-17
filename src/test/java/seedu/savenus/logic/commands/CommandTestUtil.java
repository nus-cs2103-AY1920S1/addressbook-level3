package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.NameContainsKeywordsPredicate;
import seedu.savenus.testutil.EditFoodDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_CHICKEN_RICE = "Chicken Rice";
    public static final String VALID_NAME_NASI_LEMAK = "Nasi Lemak";
    public static final String VALID_PRICE_CHICKEN_RICE = "5.80";
    public static final String VALID_PRICE_NASI_LEMAK = "4.50";
    public static final String VALID_DESCRIPTION_CHICKEN_RICE = "chicken and rice";
    public static final String VALID_DESCRIPTION_NASI_LEMAK = "rice with fried chicken";
    public static final String VALID_CATEGORY_CHICKEN_RICE = "Chinese";
    public static final String VALID_CATEGORY_NASI_LEMAK = "Malay";
    public static final String VALID_TAG_CHICKEN = "chicken";
    public static final String VALID_TAG_RICE = "rice";
    public static final String VALID_LOCATION_CHICKEN_RICE = "The Deck";
    public static final String VALID_LOCATION_NASI_LEMAK = "Frontier Canteen";
    public static final String VALID_OPENING_HOURS_CHICKEN_RICE = "0800 2100";
    public static final String VALID_OPENING_HOURS_NASI_LEMAK = "0900 1000";
    public static final String VALID_RESTRICTIONS_CHICKEN_RICE = "Not halal";
    public static final String VALID_RESTRICTIONS_NASI_LEMAK = "Contains dairy";

    public static final String NAME_DESC_CHICKEN_RICE = " " + PREFIX_NAME + VALID_NAME_CHICKEN_RICE;
    public static final String NAME_DESC_NASI_LEMAK = " " + PREFIX_NAME + VALID_NAME_NASI_LEMAK;
    public static final String PRICE_DESC_CHICKEN_RICE = " " + PREFIX_PRICE + VALID_PRICE_CHICKEN_RICE;
    public static final String PRICE_DESC_NASI_LEMAK = " " + PREFIX_PRICE + VALID_PRICE_NASI_LEMAK;
    public static final String DESCRIPTION_DESC_CHICKEN_RICE = " " + PREFIX_DESCRIPTION
                                                                    + VALID_DESCRIPTION_CHICKEN_RICE;
    public static final String DESCRIPTION_DESC_NASI_LEMAK = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_NASI_LEMAK;
    public static final String CATEGORY_DESC_CHICKEN_RICE = " " + PREFIX_CATEGORY + VALID_CATEGORY_CHICKEN_RICE;
    public static final String CATEGORY_DESC_NASI_LEMAK = " " + PREFIX_CATEGORY + VALID_CATEGORY_NASI_LEMAK;
    public static final String TAG_DESC_RICE = " " + PREFIX_TAG + VALID_TAG_RICE;
    public static final String TAG_DESC_CHICKEN = " " + PREFIX_TAG + VALID_TAG_CHICKEN;
    public static final String LOCATION_DESC_CHICKEN_RICE = " " + PREFIX_LOCATION + VALID_LOCATION_CHICKEN_RICE;
    public static final String LOCATION_DESC_NASI_LEMAK = " " + PREFIX_LOCATION + VALID_LOCATION_NASI_LEMAK;
    public static final String OPENING_HOURS_DESC_CHICKEN_RICE = " " + PREFIX_OPENING_HOURS
                                                                        + VALID_OPENING_HOURS_CHICKEN_RICE;
    public static final String OPENING_HOURS_DESC_NASI_LEMAK = " " + PREFIX_OPENING_HOURS
                                                                    + VALID_OPENING_HOURS_NASI_LEMAK;
    public static final String RESTRICTIONS_DESC_CHICKEN_RICE = " " + PREFIX_RESTRICTIONS
                                                                        + VALID_RESTRICTIONS_CHICKEN_RICE;
    public static final String RESTRICTIONS_DESC_NASI_LEMAK = " " + PREFIX_RESTRICTIONS
                                                                    + VALID_RESTRICTIONS_NASI_LEMAK;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "911a"; // 'a' not allowed in prices
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "   "; // spaces only not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "beef*"; // '*' not allowed in tags
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "    "; // spaces only not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFoodDescriptor DESC_CHICKEN_RICE;
    public static final EditCommand.EditFoodDescriptor DESC_NASI_LEMAK;

    static {
        DESC_CHICKEN_RICE = new EditFoodDescriptorBuilder().withName(VALID_NAME_CHICKEN_RICE)
                .withPrice(VALID_PRICE_CHICKEN_RICE).withDescription(VALID_DESCRIPTION_CHICKEN_RICE)
                .withTags(VALID_TAG_RICE).withLocation(VALID_LOCATION_CHICKEN_RICE)
                .withOpeningHours(VALID_OPENING_HOURS_CHICKEN_RICE).withRestrictions(VALID_RESTRICTIONS_CHICKEN_RICE)
                .build();
        DESC_NASI_LEMAK = new EditFoodDescriptorBuilder().withName(VALID_NAME_NASI_LEMAK)
                .withPrice(VALID_PRICE_NASI_LEMAK).withDescription(VALID_DESCRIPTION_NASI_LEMAK)
                .withTags(VALID_TAG_CHICKEN, VALID_TAG_RICE).withLocation(VALID_LOCATION_NASI_LEMAK)
                .withOpeningHours(VALID_OPENING_HOURS_NASI_LEMAK).withRestrictions(VALID_RESTRICTIONS_NASI_LEMAK)
                .build();
    }

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
     * - the address book, filtered food list and selected food in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Menu expectedAddressBook = new Menu(actualModel.getMenu());
        List<Food> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFoodList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getMenu());
        assertEquals(expectedFilteredList, actualModel.getFilteredFoodList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the food at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFoodAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFoodList().size());

        Food food = model.getFilteredFoodList().get(targetIndex.getZeroBased());
        final String[] splitName = food.getName().fullName.split("\\s+");
        model.updateFilteredFoodList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFoodList().size());
    }

}
