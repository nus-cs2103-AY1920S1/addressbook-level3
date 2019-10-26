package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETIC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class SuggestionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SuggestionCommand(null,
                null, null));
    }

    @Test
    public void constructor_firstArgumentNull_throwsNullPointerException() {
        String validCommandWord = ListPeopleCommand.COMMAND_WORD;
        assertThrows(NullPointerException.class, () -> new SuggestionCommand(null,
                validCommandWord, ""));
    }

    @Test
    public void constructor_secondArgumentNull_throwsNullPointerException() {
        String validCommandWord = ListPeopleCommand.COMMAND_WORD;
        assertThrows(NullPointerException.class, () -> new SuggestionCommand(validCommandWord,
                null, ""));
    }

    @Test
    public void constructor_thirdArgumentNull_throwsNullPointerException() {
        String validCommandWord = ListPeopleCommand.COMMAND_WORD;
        assertThrows(NullPointerException.class, () -> new SuggestionCommand(validCommandWord,
                validCommandWord, null));
    }

    @Test
    public void execute_successCaseWithoutArguments_throwsCommandWordException() {
        String invalidCommand = "lst";
        String suggestedCommand = ParserUtil.parseCommand(invalidCommand, "");
        SuggestionCommand suggestionCommand = new SuggestionCommand(
                invalidCommand, suggestedCommand, "");
        assertThrows(CommandWordException.class, String.format(SuggestionCommand.MESSAGE,
                invalidCommand) + suggestedCommand, () -> suggestionCommand.execute(model));
    }

    @Test
    public void execute_successCaseWithArguments_throwsCommandWordException() {
        String invalidCommand = "atag";
        String suggestedCommand = ParserUtil.parseCommand(invalidCommand, "1 " + VALID_TAG_CAR_INSURANCE);
        SuggestionCommand suggestionCommand = new SuggestionCommand(
                invalidCommand, suggestedCommand, "");
        assertThrows(CommandWordException.class, String.format(SuggestionCommand.MESSAGE,
                invalidCommand) + suggestedCommand, () -> suggestionCommand.execute(model));
    }

    @Test
    public void equals() {
        String invalidAddTagCommand = "adtg";
        String addTagArguments = "1 " + VALID_TAG_DIABETIC;
        String addTagSuggestedCommand = ParserUtil.parseCommand(invalidAddTagCommand, addTagArguments);
        String invalidListCommand = "lst";
        String listSuggestedCommand = ParserUtil.parseCommand(invalidListCommand, "");
        SuggestionCommand suggestionCommandWithAddTag = new SuggestionCommand(invalidAddTagCommand,
                addTagSuggestedCommand, addTagArguments);
        SuggestionCommand suggestionListWithList = new SuggestionCommand(invalidListCommand,
                listSuggestedCommand, "");
        // same object -> returns true
        assertTrue(suggestionCommandWithAddTag.equals(suggestionCommandWithAddTag));

        // same values -> returns true
        SuggestionCommand addTagSuggestionCommandCopy = new SuggestionCommand(invalidAddTagCommand,
                addTagSuggestedCommand, addTagArguments);
        assertTrue(suggestionCommandWithAddTag.equals(addTagSuggestionCommandCopy));

        // different original values -> returns false
        SuggestionCommand differentOriginal = new SuggestionCommand(invalidListCommand,
                addTagSuggestedCommand, addTagArguments);
        assertFalse(suggestionCommandWithAddTag.equals(differentOriginal));

        // different suggested values -> returns false
        SuggestionCommand differentSuggestion = new SuggestionCommand(invalidAddTagCommand,
                listSuggestedCommand, addTagArguments);
        assertFalse(suggestionCommandWithAddTag.equals(differentSuggestion));

        // different argument values -> returns false
        SuggestionCommand differentArguments = new SuggestionCommand(invalidAddTagCommand,
                addTagSuggestedCommand, "");
        assertFalse(suggestionCommandWithAddTag.equals(differentArguments));

        // different suggested and argument values -> returns false
        SuggestionCommand differentSuggestedAndArgument = new SuggestionCommand(invalidAddTagCommand,
                listSuggestedCommand, "");
        assertFalse(suggestionCommandWithAddTag.equals(differentSuggestedAndArgument));

        // different original and suggested values -> returns false
        SuggestionCommand differentOriginalAndSuggested = new SuggestionCommand(invalidListCommand,
                listSuggestedCommand, addTagArguments);
        assertFalse(suggestionCommandWithAddTag.equals(differentOriginalAndSuggested));

        // different original and argument values -> returns false
        SuggestionCommand differentOriginalAndArgument = new SuggestionCommand(invalidAddTagCommand,
                addTagSuggestedCommand, "");
        assertFalse(suggestionCommandWithAddTag.equals(differentOriginalAndArgument));

        // different types -> returns false
        assertFalse(addTagSuggestedCommand.equals(1));

        // null -> returns false
        assertFalse(addTagSuggestedCommand.equals(null));

        // different Policy -> returns false
        assertFalse(addTagSuggestedCommand.equals(listSuggestedCommand));
    }
}
