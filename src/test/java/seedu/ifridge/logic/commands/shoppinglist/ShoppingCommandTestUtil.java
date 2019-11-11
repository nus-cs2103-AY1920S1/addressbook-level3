package seedu.ifridge.logic.commands.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.ShoppingNameContainsKeywordsPredicate;
import seedu.ifridge.testutil.EditShoppingItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class ShoppingCommandTestUtil {

    public static final String VALID_NAME_NUTS = "Peanuts";
    public static final String VALID_NAME_ORANGES = "Oranges";
    public static final String VALID_NAME_CHEESE = "Cheddar Cheese";
    public static final String VALID_NAME_TOMATO_JUICE = "Tomato Juice";
    public static final String VALID_AMOUNT_NUTS = "120g";
    public static final String VALID_AMOUNT_ORANGES = "15units";
    public static final String VALID_AMOUNT_CHEESE = "300g";
    public static final String VALID_AMOUNT_TOMATO_JUICE = "300ml";

    public static final String NAME_DESC_NUTS = " " + PREFIX_NAME + VALID_NAME_NUTS;
    public static final String NAME_DESC_ORANGES = " " + PREFIX_NAME + VALID_NAME_ORANGES;
    public static final String NAME_DESC_CHEESE = " " + PREFIX_NAME + VALID_NAME_CHEESE;
    public static final String NAME_DESC_TOMATO_JUICE = " " + PREFIX_NAME + VALID_NAME_TOMATO_JUICE;
    public static final String AMOUNT_DESC_NUTS = " " + PREFIX_AMOUNT + VALID_AMOUNT_NUTS;
    public static final String AMOUNT_DESC_ORANGES = " " + PREFIX_AMOUNT + VALID_AMOUNT_ORANGES;
    public static final String AMOUNT_DESC_CHEESE = " " + PREFIX_AMOUNT + VALID_AMOUNT_CHEESE;
    public static final String AMOUNT_DESC_TOMATO_JUICE = " " + PREFIX_AMOUNT + VALID_NAME_TOMATO_JUICE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Mango&"; // '&' not allowed in names
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "300D"; // 'D' is not a valid unit

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditShoppingCommand.EditShoppingItemDescriptor DESC_NUTS;
    public static final EditShoppingCommand.EditShoppingItemDescriptor DESC_ORANGES;

    static {
        DESC_NUTS = new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_NUTS).withAmount(VALID_AMOUNT_NUTS)
                .build();
        DESC_ORANGES = new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_ORANGES)
                .withAmount(VALID_AMOUNT_ORANGES).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ShoppingList expectedShoppingList = new ShoppingList(actualModel.getShoppingList());
        List<ShoppingItem> expectedFilteredList = new ArrayList<>(actualModel.getFilteredShoppingList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedShoppingList, actualModel.getShoppingList());
        assertEquals(expectedFilteredList, actualModel.getFilteredShoppingList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the shoppingItem at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showShoppingItemAtIndex(Model model, Index targetIndex) {
        // For grocery list
        assertTrue(targetIndex.getZeroBased() < model.getFilteredShoppingList().size());
        ShoppingItem shoppingItem = model.getFilteredShoppingList().get(targetIndex.getZeroBased());
        final String[] splitName = shoppingItem.getName().fullName.split("\\s+");
        model.updateFilteredShoppingList(new ShoppingNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        assertEquals(1, model.getFilteredShoppingList().size());
    }

}
