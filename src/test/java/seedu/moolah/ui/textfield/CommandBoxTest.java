package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.ui.textfield.CommandTextField.ARGUMENT_STYLE_PREFIX;
import static seedu.moolah.ui.textfield.CommandTextField.COMMAND_WORD_STYLE;
import static seedu.moolah.ui.textfield.CommandTextField.ERROR_STYLE_CLASS;
import static seedu.moolah.ui.textfield.CommandTextField.PREFIX_STYLE_PREFIX;
import static seedu.moolah.ui.textfield.CommandTextField.STRING_STYLE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.ui.GuiUnitTest;
import seedu.moolah.ui.panel.PanelName;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

/**
 * Contains tests for {@code CommandBox}.
 *
 * Adapted from (with major modifications):
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/CommandBoxTest.java
 */
public class CommandBoxTest extends GuiUnitTest {

    private static final String PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED = "PREFIX00/";
    private static final String PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED1 = "PREFIX01/";
    private static final String PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED2 = "PREFIX02/";
    private static final String PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED3 = "PREFIX03/";
    private static final String PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED4 = "PREFIX04/";
    private static final String PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL = "PREFIX1/";
    private static final String PREFIX_THAT_IS_NOT_SUPPORTED = "PREFIX2/";

    private static final String COMMAND_THAT_SUCCEEDS = "COMMAND";
    private static final String COMMAND_THAT_IS_SUPPORTED1 = "COMMAND1";
    private static final String COMMAND_THAT_IS_SUPPORTED2 = "COMMAND2";
    private static final String COMMAND_THAT_IS_SUPPORTED3 = "COMMAND3";

    private static final String WHITESPACE = "       ";

    private static final String INPUT_THAT_SUCCEEDS1 = COMMAND_THAT_IS_SUPPORTED1
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL;

    private static final String INPUT_THAT_SUCCEEDS2 = COMMAND_THAT_IS_SUPPORTED2
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED
            + PREFIX_THAT_IS_NOT_SUPPORTED + WHITESPACE
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL;

    private static final String INPUT_THAT_SUCCEEDS3 = COMMAND_THAT_IS_SUPPORTED3
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED1
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED2
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED3
            + " " + PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED4;

    private static class PrefixStub extends Prefix {
        static final PrefixStub SUPPORTED_AND_REQUIRED = new PrefixStub(PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED);
        static final PrefixStub SUPPORTED_AND_REQUIRED1 = new PrefixStub(PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED1);
        static final PrefixStub SUPPORTED_AND_REQUIRED2 = new PrefixStub(PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED2);
        static final PrefixStub SUPPORTED_AND_REQUIRED3 = new PrefixStub(PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED3);
        static final PrefixStub SUPPORTED_AND_REQUIRED4 = new PrefixStub(PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED4);
        static final PrefixStub SUPPORTED_AND_OPTIONAL = new PrefixStub(PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL);
        private PrefixStub(String prefix) {
            super(prefix, prefix);
        }
    }

    private static final String COMMAND_THAT_CAUSES_PARSE_EXCEPTION = "INVALIDP";
    private static final String COMMAND_THAT_CAUSES_COMMAND_EXCEPTION = "INVALIDC";
    private static final String COMMAND_THAT_CAUSES_UNMAPPEDPANEL_EXCEPTION = "INVALIDU";

    private CommandBoxHandle commandBoxHandle;

    private Runnable disableCommandThatSucceeds;

    @BeforeEach
    void setUp() {
        CommandBox commandBox = new CommandBox(input -> {
            switch (input) {
            case COMMAND_THAT_SUCCEEDS:
                //fallthrough
            case INPUT_THAT_SUCCEEDS1:
                //fallthrough
            case INPUT_THAT_SUCCEEDS2:
                //fallthrough
            case INPUT_THAT_SUCCEEDS3:
                return new CommandResult("successful" + input);
            case COMMAND_THAT_CAUSES_COMMAND_EXCEPTION:
                throw new CommandException("exception");
            case COMMAND_THAT_CAUSES_PARSE_EXCEPTION:
                throw new ParseException("exception");
            case COMMAND_THAT_CAUSES_UNMAPPEDPANEL_EXCEPTION:
                throw new UnmappedPanelException(new PanelName("exception"));
            default:
                throw new AssertionError("An unexpected command was input");
            }
        });

        commandBoxHandle =
                new CommandBoxHandle(getChildNode(commandBox.getRoot(), CommandBoxHandle.COMMAND_INPUT_FIELD_ID));

        getChildNode(commandBox.getRoot(), CommandBoxHandle.COMMAND_INPUT_FIELD_ID);

        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                COMMAND_THAT_SUCCEEDS,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                COMMAND_THAT_IS_SUPPORTED1,
                List.of(PrefixStub.SUPPORTED_AND_REQUIRED),
                List.of(PrefixStub.SUPPORTED_AND_OPTIONAL));
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                COMMAND_THAT_IS_SUPPORTED2,
                List.of(PrefixStub.SUPPORTED_AND_REQUIRED),
                List.of(PrefixStub.SUPPORTED_AND_OPTIONAL));
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                COMMAND_THAT_IS_SUPPORTED3,
                List.of(
                        PrefixStub.SUPPORTED_AND_REQUIRED,
                        PrefixStub.SUPPORTED_AND_REQUIRED1,
                        PrefixStub.SUPPORTED_AND_REQUIRED2,
                        PrefixStub.SUPPORTED_AND_REQUIRED3,
                        PrefixStub.SUPPORTED_AND_REQUIRED4
                ),
                Collections.emptyList());
        commandBox.enableSyntaxHighlighting();
        uiPartExtension.setUiPart(commandBox);


        disableCommandThatSucceeds = () -> {
            commandBox.disableSuggestionsAndSyntaxHighlightingFor(COMMAND_THAT_SUCCEEDS);
        };
    }

    @Test
    void enterInput_commandThatCausesParseException_styleSpansSetToError() {
        commandBoxHandle.run(COMMAND_THAT_CAUSES_PARSE_EXCEPTION);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(ERROR_STYLE_CLASS), commandBoxHandle.getInput().length());
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }

    @Test
    void enterInput_commandThatCausesCommandException_styleSpansSetToError() {
        commandBoxHandle.run(COMMAND_THAT_CAUSES_COMMAND_EXCEPTION);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(ERROR_STYLE_CLASS), commandBoxHandle.getInput().length());
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }

    @Test
    void highlighting_commandWordFollowedByPreamble_matchesExpected() {
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND_THAT_SUCCEEDS.length());
        expected.add(Collections.emptyList(), WHITESPACE.length());
        expected.add(Collections.singleton(STRING_STYLE), 10);
        commandBoxHandle.type(COMMAND_THAT_SUCCEEDS);
        commandBoxHandle.type(WHITESPACE);
        commandBoxHandle.type("1234567890");
        guiRobot.sleep(400);
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }

    @Test
    void highlighting_commandWordFollowedBySupportedPrefixes_matchesExpected() {
        commandBoxHandle.type(INPUT_THAT_SUCCEEDS1);
        guiRobot.sleep(400);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND_THAT_IS_SUPPORTED1.length());
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "0"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "1"),
                PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL.length() + 1);
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }


    @Test
    void highlighting_commandWordWithSupportedAndUnsupportedPrefix_matchesExpected() {
        commandBoxHandle.type(INPUT_THAT_SUCCEEDS2);
        guiRobot.sleep(400);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND_THAT_IS_SUPPORTED2.length());
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "0"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        expected.add(
                Collections.singleton(ARGUMENT_STYLE_PREFIX + "0"), PREFIX_THAT_IS_NOT_SUPPORTED.length());
        expected.add(
                Collections.emptyList(), WHITESPACE.length());
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "1"),
                PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL.length() + 1);
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }

    @Test
    void highlighting_commandWordAndMoreThanFourPrefixes_matchesExpected() {
        commandBoxHandle.type(INPUT_THAT_SUCCEEDS3);
        guiRobot.sleep(400);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND_THAT_IS_SUPPORTED3.length());
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "0"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "1"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "2"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "3"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        expected.add(
                Collections.singleton(PREFIX_STYLE_PREFIX + "0"),
                PREFIX_THAT_IS_SUPPORTED_AND_REQUIRED.length() + 1);
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }

    @Test
    void enterInput_commandThatCausesUnmappedPanelException_styleSpansSetToError() {
        commandBoxHandle.run(COMMAND_THAT_CAUSES_UNMAPPEDPANEL_EXCEPTION);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(ERROR_STYLE_CLASS), commandBoxHandle.getInput().length());
        assertEquals(expected.create(), commandBoxHandle.getStyleSpans());
    }

    @Test
    void enterInput_commandThatSucceeds_textClearedAndStoredInHistory() {
        commandBoxHandle.type(COMMAND_THAT_SUCCEEDS);
        guiRobot.sleep(400);
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(COMMAND_WORD_STYLE), commandBoxHandle.getInput().length());
        commandBoxHandle.pushKey(KeyCode.ENTER);
        assertTrue(commandBoxHandle.getInput().isEmpty());
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
    }

    @Test
    void upKey_noCommandInHistory_noChange() {
        commandBoxHandle.type(COMMAND_THAT_SUCCEEDS);
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
    }

    @Test
    void downKey_noCommandInHistory_noChange() {
        commandBoxHandle.type(COMMAND_THAT_SUCCEEDS);
        commandBoxHandle.pushKey(KeyCode.DOWN);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.DOWN);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
    }

    @Test
    void upDownKeys_commandInHistory_canGoBackAndForth() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        commandBoxHandle.run(INPUT_THAT_SUCCEEDS1);
        commandBoxHandle.run(INPUT_THAT_SUCCEEDS2);
        commandBoxHandle.run(INPUT_THAT_SUCCEEDS3);
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(INPUT_THAT_SUCCEEDS3, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(INPUT_THAT_SUCCEEDS2, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(INPUT_THAT_SUCCEEDS1, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.UP);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.DOWN);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.DOWN);
        assertEquals(INPUT_THAT_SUCCEEDS1, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.DOWN);
        assertEquals(INPUT_THAT_SUCCEEDS2, commandBoxHandle.getInput());
        commandBoxHandle.pushKey(KeyCode.DOWN);
        assertEquals(INPUT_THAT_SUCCEEDS3, commandBoxHandle.getInput());
    }

    @Test
    void tabKeyEnablesSuggestions_noInput_showsAll() {
        assertFalse(commandBoxHandle.isSuggestionMenuShowing());
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertEquals(4, commandBoxHandle.suggestionCount());
        assertTrue(commandBoxHandle.isSuggestionMenuShowing());
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertFalse(commandBoxHandle.isSuggestionMenuShowing());
    }

    @Test
    void commandIsDisabled_pressTab_showsOneLessCommandAfter() {
        assertFalse(commandBoxHandle.isSuggestionMenuShowing());
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertEquals(4, commandBoxHandle.suggestionCount());
        assertTrue(commandBoxHandle.isSuggestionMenuShowing());
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertFalse(commandBoxHandle.isSuggestionMenuShowing());
        disableCommandThatSucceeds.run();
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertTrue(commandBoxHandle.isSuggestionMenuShowing());
        assertEquals(3, commandBoxHandle.suggestionCount());
    }

    @Test
    void leftArrowKeyHidesSuggestions_leftKey_menuIsHidden() {
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertTrue(commandBoxHandle.isSuggestionMenuShowing());
        commandBoxHandle.pushKey(KeyCode.LEFT);
        assertFalse(commandBoxHandle.isSuggestionMenuShowing());
    }

    @Test
    void rightArrowKeyHidesSuggestions_rightKey_menuIsHidden() {
        commandBoxHandle.pushKey(KeyCode.TAB);
        assertTrue(commandBoxHandle.isSuggestionMenuShowing());
        commandBoxHandle.pushKey(KeyCode.RIGHT);
        assertFalse(commandBoxHandle.isSuggestionMenuShowing());
    }

    @Test
    void suggestionMenu_selectItem_appendsChoice() {
        commandBoxHandle.pushKey(KeyCode.TAB);
        commandBoxHandle.fireActionEvent(0);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
    }

    @Test
    void suggestionMenu_partialInputSelectItem_completesChoice() {
        commandBoxHandle.type(COMMAND_THAT_SUCCEEDS.substring(0, 2));
        commandBoxHandle.pushKey(KeyCode.TAB);
        commandBoxHandle.fireActionEvent(0);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());
    }

    @Test
    void suggestionMenu_supportedCommandInputWithSpace_showsPrefixes() {
        commandBoxHandle.pushKey(KeyCode.TAB);
        commandBoxHandle.type(COMMAND_THAT_IS_SUPPORTED2);
        commandBoxHandle.type(" ");
        assertEquals(4, commandBoxHandle.suggestionCount());
        commandBoxHandle.fireActionEvent(3);
        assertEquals(
                COMMAND_THAT_IS_SUPPORTED2 + " " + PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL,
                commandBoxHandle.getInput()
        );
    }

    @Test
    void suggestionMenu_inputEndsNoSpace_appendsPrefixWIthSpace() {
        String inputWithoutTailingSpace = " t";
        commandBoxHandle.pushKey(KeyCode.TAB);
        commandBoxHandle.type(COMMAND_THAT_IS_SUPPORTED2);
        commandBoxHandle.type(inputWithoutTailingSpace);
        commandBoxHandle.fireActionEvent(3);
        assertEquals(
                COMMAND_THAT_IS_SUPPORTED2 + inputWithoutTailingSpace + " " + PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL,
                commandBoxHandle.getInput()
        );
    }

    @Test
    void suggestionMenu_inputEndsWithSpace_appendsPrefixWithoutSpace() {
        String inputWithTailingSpace = " t ";
        commandBoxHandle.pushKey(KeyCode.TAB);
        commandBoxHandle.type(COMMAND_THAT_IS_SUPPORTED2);
        commandBoxHandle.type(inputWithTailingSpace);
        commandBoxHandle.fireActionEvent(3);
        assertEquals(
                COMMAND_THAT_IS_SUPPORTED2 + inputWithTailingSpace + PREFIX_THAT_IS_SUPPORTED_AND_OPTIONAL,
                commandBoxHandle.getInput()
        );
    }

    @Test
    void suggestionMenu_supportedCommandInputWithSpaceChooseAllMissing_showsPrefixesAndAppendsAllAsExpected() {
        commandBoxHandle.pushKey(KeyCode.TAB);
        commandBoxHandle.type(COMMAND_THAT_IS_SUPPORTED3);
        commandBoxHandle.type(" ");
        assertEquals(6, commandBoxHandle.suggestionCount());
        commandBoxHandle.fireActionEvent(5);
        assertEquals(INPUT_THAT_SUCCEEDS3, commandBoxHandle.getInput());
    }
}
