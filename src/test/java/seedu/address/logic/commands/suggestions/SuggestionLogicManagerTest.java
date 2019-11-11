package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.ui.SuggestingCommandBox.SuggestionLogic;
import seedu.address.ui.SuggestingCommandBox.SuggestionLogic.SelectionResult;

class SuggestionLogicManagerTest {
    static final String EMPTY_STRING = "";

    private static final List<String> COMMAND_WORDS = List.of(
            "myfirstcommand",
            "myothercommand",
            "thirdcommand"
    );

    private ModelManager model;
    private SuggestionLogic suggestionLogic;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
        suggestionLogic = new SuggestionLogicManager(model, COMMAND_WORDS);
    }

    @Test
    void constructor_validArgumentDefaultCommandWords_success() {
        assertDoesNotThrow(() -> {
            new SuggestionLogicManager(model);
        });
    }

    @Test
    void constructor_validArguments_success() {
        assertDoesNotThrow(() -> {
            new SuggestionLogicManager(model, COMMAND_WORDS);
        });
    }

    @Test
    void constructor_observableListCommandWords_success() {
        final ObservableList<String> commandWordsObservableList = FXCollections.observableList(COMMAND_WORDS);
        assertDoesNotThrow(() -> {
            new SuggestionLogicManager(model, commandWordsObservableList);
        });
    }

    @Test
    void constructor_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new SuggestionLogicManager(null, COMMAND_WORDS);
        });
    }

    @Test
    void constructor_nullCommandWords_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new SuggestionLogicManager(model, null);
        });
    }

    @Test
    void getSuggestions_prefixTrailingSpace_oneSuggestion() {
        final String commandSharedPrefix = "myfirst ";
        final List<String> expectedSuggestions = COMMAND_WORDS.subList(0, 1);

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_sharedPrefixTrailingSpace_twoSuggestions() {
        final String commandSharedPrefix = "my ";
        final List<String> expectedSuggestions = COMMAND_WORDS.subList(0, 2);

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_uniquePrefixTrailingSpace_oneSuggestion() {
        final String commandSharedPrefix = "thi ";
        final List<String> expectedSuggestions = COMMAND_WORDS.subList(2, 3);

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_uniqueFuzzyMatchingTrailingSpace_oneSuggestion() {
        final String commandSharedPrefix = "tc ";
        final List<String> expectedSuggestions = COMMAND_WORDS.subList(2, 3);

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_sharedFuzzyMatchingTrailingSpace_twoSuggestions() {
        final String commandSharedPrefix = "mc ";
        final List<String> expectedSuggestions = COMMAND_WORDS.subList(0, 2);

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_fuzzyFailingSubstringMatchingTrailingSpace_noSuggestions() {
        final String commandSharedPrefix = "command ";
        final List<String> expectedSuggestions = List.of();

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_fuzzyFailingTrailingSpace_noSuggestion() {
        final String commandSharedPrefix = "tf ";
        final List<String> expectedSuggestions = List.of();

        for (int caretPosition = 0; caretPosition <= commandSharedPrefix.length(); caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(commandSharedPrefix, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_caretWithinCommandWordSection_commandWordSuggestions() {
        final String commandWord = "myfirst ";
        final String commandArguments = "test/some argument";
        final String command = commandWord + commandArguments;
        final int commandWordBounds = command.indexOf(commandArguments) - 1;
        final List<String> expectedSuggestions = COMMAND_WORDS.subList(0, 1);

        for (int caretPosition = 0; caretPosition <= commandWordBounds; caretPosition++) {
            final List<String> actualSuggestions = suggestionLogic.getSuggestions(command, caretPosition);
            assertEquals(expectedSuggestions, actualSuggestions);
        }
    }

    @Test
    void getSuggestions_emptyCommand_noSuggestions() {
        final String command = EMPTY_STRING;
        final List<String> expectedSuggestions = List.of();

        final List<String> actualSuggestions = suggestionLogic.getSuggestions(command, 0);
        assertEquals(expectedSuggestions, actualSuggestions);
    }

    private void assertSelectionResultEquals(final String expectedCommand, final int expectedPosition,
                                             final SelectionResult actual) {
        assertEquals(expectedCommand, actual.commandText);
        assertEquals(expectedPosition, actual.caretPosition);
    }

    @Test
    void selectSuggestion_emptyCommand_noChange() {
        final String command = EMPTY_STRING;
        final String expectedCommand = command;
        final int expectedPosition = 0;

        final SelectionResult actualSuggestions = suggestionLogic.selectSuggestion(
                command, 0, "invalid");
        assertSelectionResultEquals(expectedCommand, expectedPosition, actualSuggestions);
    }

    @Test
    void selectSuggestion_selectedCommandWord_commandWordUpdated() {
        final String command = "mc";
        final int commandWordBounds = command.length();
        final String selectedCommand = "my-command";
        final String expectedCommand = selectedCommand + " ";
        final int expectedPosition = expectedCommand.length();

        for (int caretPosition = 0; caretPosition <= commandWordBounds; caretPosition++) {
            final SelectionResult selectionResult = suggestionLogic.selectSuggestion(
                    command, caretPosition, selectedCommand);
            assertSelectionResultEquals(expectedCommand, expectedPosition, selectionResult);
        }
    }

    @Test
    void selectSuggestion_selectedCommandWord_argumentsPreserved() {
        final String commandWord = "mc ";
        final String commandArguments = "test/some argument";
        final String command = commandWord + commandArguments;
        final int commandWordBounds = command.indexOf(commandArguments) - 1;

        final String selectedCommand = "my-command";
        final String expectedCommand = selectedCommand + " " + commandArguments;
        final int expectedPosition = selectedCommand.length() + 1;

        for (int caretPosition = 0; caretPosition <= commandWordBounds; caretPosition++) {
            final SelectionResult selectionResult = suggestionLogic.selectSuggestion(
                    command, caretPosition, selectedCommand);
            assertSelectionResultEquals(expectedCommand, expectedPosition, selectionResult);
        }
    }

}
