//package seedu.address.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.AddressBook;
//import seedu.address.model.Model;
//import seedu.address.model.person.DescriptionContainsKeywordsPredicate;
//
//import seedu.address.model.person.Entry;
//import seedu.address.testutil.EditEntryDescriptorBuilder;
//
///**
// * Contains helper methods for testing commands.
// */
//public class CommandTestUtil {
//
//    public static final String VALID_DESC_FOOD_EXPENSE = "deck mala";
//    public static final String VALID_DESC_CLOTHING_EXPENSE = "cotton on jeans";
//    public static final String VALID_TAG_FOOD = "food";
//    public static final String VALID_TAG_CLOTHING = "clothing";
//
//    public static final String NAME_DESC_FOOD_EXPENSE = " " + PREFIX_DESC + VALID_DESC_FOOD_EXPENSE;
//    public static final String NAME_DESC_CLOTHING_EXPENSE = " " + PREFIX_DESC + VALID_DESC_CLOTHING_EXPENSE;
//    public static final String TAG_DESC_FOOD = " " + PREFIX_TAG + VALID_TAG_FOOD;
//    public static final String TAG_DESC_CLOTHING = " " + PREFIX_TAG + VALID_TAG_CLOTHING;
//
//    public static final String INVALID_NAME_DESC = " " + PREFIX_DESC + "deck mala&"; // '&' not allowed in names
//    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "food*"; // '*' not allowed in tags
//
//    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
//    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
//
//    public static final EditCommand.EditEntryDescriptor DESC_FOOD_EXPENSE;
//    public static final EditCommand.EditEntryDescriptor DESC_CLOTHING_EXPENSE;
//
//    static {
//        DESC_FOOD_EXPENSE = new EditEntryDescriptorBuilder().withDescription(VALID_DESC_FOOD_EXPENSE)
//                .withTags(VALID_TAG_FOOD).build();
//        DESC_CLOTHING_EXPENSE = new EditEntryDescriptorBuilder().withDescription(VALID_DESC_CLOTHING_EXPENSE)
//                .withTags(VALID_TAG_CLOTHING).build();
//    }
//
//    /**
//     * Executes the given {@code command}, confirms that <br>
//     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
//     * - the {@code actualModel} matches {@code expectedModel}
//     */
//    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
//            Model expectedModel) {
//        try {
//            CommandResult result = command.execute(actualModel);
//            assertEquals(expectedCommandResult, result);
//            assertEquals(expectedModel, actualModel);
//        } catch (CommandException ce) {
//            throw new AssertionError("Execution of command should not fail.", ce);
//        }
//    }
//
//    /**
//     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
//     * that takes a string {@code expectedMessage}.
//     */
//    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
//            Model expectedModel) {
//        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
//        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
//    }
//
//    /**
//     * Executes the given {@code command}, confirms that <br>
//     * - a {@code CommandException} is thrown <br>
//     * - the CommandException message matches {@code expectedMessage} <br>
//     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
//     */
//    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
//        // we are unable to defensively copy the model for comparison later, so we can
//        // only do so by copying its components.
//        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
//        List<Entry> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEntryList());
//
//        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
//        assertEquals(expectedAddressBook, actualModel.getAddressBook());
//        assertEquals(expectedFilteredList, actualModel.getFilteredEntryList());
//    }
//    /**
//     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
//     * {@code model}'s address book.
//     */
//    public static void showPersonAtIndex(Model model, Index targetIndex) {
//        assertTrue(targetIndex.getZeroBased() < model.getFilteredEntryList().size());
//
//        Entry person = model.getFilteredEntryList().get(targetIndex.getZeroBased());
//        final String[] splitDesc = person.getDesc().fullDesc.split("\\s+");
//        model.updateFilteredEntryList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitDesc[0])));
//
//        assertEquals(1, model.getFilteredEntryList().size());
//    }
//
//}
