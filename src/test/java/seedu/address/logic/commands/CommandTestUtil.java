package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ABRA = "Abra";
    public static final String VALID_NAME_BUTTERFREE = "Butterfree";
    public static final String VALID_DESCRIPTION_ABRA = "It sleeps eighteen hours a day, but employs telekinesis "
            + "even while sleeping.";
    public static final String VALID_DESCRIPTION_BUTTERFREE = "Its wings are covered with poisonous dust. If you see "
            + "one flapping its wings, be careful not to inhale any of the dust.";
    public static final String VALID_TAG_PSYCHIC = "psychic";
    public static final String VALID_TAG_BUG = "bug";

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_WORD + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_WORD + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_MEANING + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_MEANING + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_WORD + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_MEANING + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    //    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    //
    //    static {
    //        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
    //                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //                .withTags(VALID_TAG_FRIEND).build();
    //        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    //    }
    //
    //    /**
    //     * Executes the given {@code command}, confirms that <br>
    //     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
    //     * - the {@code actualModel} matches {@code expectedModel}
    //     */
    //    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult
    //            expectedCommandResult, Model expectedModel) {
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
    //        WordBank expectedWordBank = new WordBank(actualModel.getWordBank());
    //        List<Card> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCardList());
    //
    //        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
    //        assertEquals(expectedWordBank, actualModel.getWordBank());
    //        assertEquals(expectedFilteredList, actualModel.getFilteredCardList());
    //    }
    //    /**
    //     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
    //     * {@code model}'s address book.
    //     */
    //    public static void showPersonAtIndex(Model model, Index targetIndex) {
    //        assertTrue(targetIndex.getZeroBased() < model.getFilteredCardList().size());
    //
    //        Card person = model.getFilteredCardList().get(targetIndex.getZeroBased());
    //        final String[] splitName = person.getName().fullName.split("\\s+");
    //        model.updateFilteredCardList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));
    //
    //        assertEquals(1, model.getFilteredCardList().size());
    //    }
}
