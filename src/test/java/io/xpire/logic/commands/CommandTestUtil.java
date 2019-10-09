package io.xpire.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.CliSyntax;
import io.xpire.model.Model;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.NameContainsKeywordsPredicate;
import io.xpire.testutil.Assert;
import io.xpire.testutil.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_KIWI = "Kiwi";
    public static final String VALID_NAME_APPLE = "Apple";
    public static final String VALID_NAME_BANANA = "Banana";
    public static final String VALID_NAME_MILK = "Milk";

    public static final String VALID_EXPIRY_DATE_KIWI = "01/02/2019";
    public static final String VALID_EXPIRY_DATE_APPLE = "01/02/2019";
    public static final String VALID_EXPIRY_DATE_BANANA = "01/02/2019";
    public static final String VALID_EXPIRY_DATE_MILK = "01/02/2019";

    public static final String VALID_TAG_FRUIT = "fruit";
    public static final String VALID_TAG_DRINK = "drinks";
    public static final String VALID_TAG_GREEN = "green";

    public static final String NAME_DESC_KIWI = " " + CliSyntax.PREFIX_NAME + VALID_NAME_KIWI;
    public static final String NAME_DESC_APPLE = " " + CliSyntax.PREFIX_NAME + VALID_NAME_APPLE;
    public static final String NAME_DESC_BANANA = " " + CliSyntax.PREFIX_NAME + VALID_NAME_BANANA;
    public static final String NAME_DESC_MILK = " " + CliSyntax.PREFIX_NAME + VALID_NAME_MILK;

    public static final String EXPIRY_DATE_DESC_KIWI = " " + CliSyntax.PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_KIWI;
    public static final String EXPIRY_DATE_DESC_APPLE = " " + CliSyntax.PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_APPLE;
    public static final String EXPIRY_DATE_DESC_BANANA = " " + CliSyntax.PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_BANANA;
    public static final String EXPIRY_DATE_DESC_MILK = " " + CliSyntax.PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_MILK;
    public static final String TAG_DESC_FRUIT = " " + CliSyntax.PREFIX_TAG + VALID_TAG_FRUIT;
    public static final String TAG_DESC_DRINK = " " + CliSyntax.PREFIX_TAG + VALID_TAG_DRINK;
    public static final String TAG_DESC_GREEN = " " + CliSyntax.PREFIX_TAG + VALID_TAG_GREEN;

    public static final String INVALID_NAME_DESC = " " + CliSyntax.PREFIX_NAME + "James&";
    public static final String INVALID_PHONE_DESC = " " + CliSyntax.PREFIX_PHONE + "911a";
    public static final String INVALID_EMAIL_DESC = " " + CliSyntax.PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + CliSyntax.PREFIX_ADDRESS;
    public static final String INVALID_EXPIRY_DATE_DESC = " " + CliSyntax.PREFIX_EXPIRY_DATE + "";
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_NAME = "James&";
    public static final String INVALID_EXPIRY_DATE = "";
    public static final String INVALID_TAG = "hubby*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditItemDescriptor DESC_KIWI;
    public static final EditCommand.EditItemDescriptor DESC_APPLE;

    static {
        DESC_KIWI = new EditItemDescriptorBuilder().withName(VALID_NAME_KIWI)
                                                  .withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                                                  .withTags(VALID_TAG_FRUIT).build();
        DESC_APPLE = new EditItemDescriptorBuilder().withName(VALID_NAME_BANANA)
                                                    .withExpiryDate(VALID_EXPIRY_DATE_BANANA)
                                                  .withTags(VALID_TAG_FRUIT).build();
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
     * - the expiry date tracker, filtered item list and selected item in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Xpire expectedXpire = new Xpire(actualModel.getXpire());
        List<Item> expectedFilteredList = new ArrayList<>(actualModel.getFilteredItemList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedXpire, actualModel.getXpire());
        assertEquals(expectedFilteredList, actualModel.getFilteredItemList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s expiry date tracker.
     */
    public static void showItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredItemList().size());

        Item item = model.getFilteredItemList().get(targetIndex.getZeroBased());
        final String[] splitName = item.getName().toString().split("\\s+");
        model.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredItemList().size());
    }

}
