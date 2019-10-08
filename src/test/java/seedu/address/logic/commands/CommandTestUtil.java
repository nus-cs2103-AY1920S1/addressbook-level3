package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.WordContainsKeywordsPredicate;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.EditCardDescriptorBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_WORD_ABRA = "Abra";
    public static final String VALID_WORD_BUTTERFREE = "Butterfree";
    public static final String VALID_MEANING_ABRA = "It sleeps eighteen hours a day, but employs telekinesis "
            + "even while sleeping.";
    public static final String VALID_MEANING_BUTTERFREE = "Its wings are covered with poisonous dust. If you see "
            + "one flapping its wings, be careful not to inhale any of the dust.";
    public static final String VALID_TAG_PSYCHIC = "psychic";
    public static final String VALID_TAG_BUG = "bug";


    public static final String WORD_DESC_ABRA = " " + PREFIX_WORD + VALID_WORD_ABRA;
    public static final String WORD_DESC_BUTTERFREE = " " + PREFIX_WORD + VALID_WORD_BUTTERFREE;
    public static final String MEANING_DESC_ABRA = " " + PREFIX_MEANING + VALID_MEANING_ABRA;
    public static final String MEANING_DESC_BUTTERFREE = " " + PREFIX_MEANING + VALID_MEANING_BUTTERFREE;
    public static final String TAG_DESC_PSYCHIC = " " + PREFIX_TAG + VALID_TAG_PSYCHIC;
    public static final String TAG_DESC_BUG = " " + PREFIX_TAG + VALID_TAG_BUG;

    public static final String INVALID_WORD_DESC = " " + PREFIX_WORD + "      "; // all white spaces not allowed
    public static final String INVALID_MEANING_DESC = " " + PREFIX_MEANING + "     "; // all white spaces not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCardDescriptor DESC_ABRA;
    public static final EditCommand.EditCardDescriptor DESC_BUTTERFREE;

    static {
        DESC_ABRA = new EditCardDescriptorBuilder().withWord(VALID_WORD_ABRA)
                .withMeaning(VALID_MEANING_ABRA)
                .withTags(VALID_TAG_PSYCHIC).build();
        DESC_BUTTERFREE = new EditCardDescriptorBuilder().withWord(VALID_WORD_BUTTERFREE)
                .withMeaning(VALID_MEANING_BUTTERFREE)
                .withTags(VALID_TAG_BUG).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult
            expectedCommandResult, Model expectedModel) {
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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WordBank expectedWordBank = new WordBank(actualModel.getWordBank());
        List<Card> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedWordBank, actualModel.getWordBank());
        assertEquals(expectedFilteredList, actualModel.getFilteredCardList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the card at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCardList().size());

        Card card = model.getFilteredCardList().get(targetIndex.getZeroBased());
        final String[] splitName = card.getWord().value.split("\\s+");
        model.updateFilteredCardList(new WordContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCardList().size());
    }
}
