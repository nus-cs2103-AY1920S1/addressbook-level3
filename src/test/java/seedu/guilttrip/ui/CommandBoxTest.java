package seedu.guilttrip.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

import javafx.scene.input.KeyCode;

import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.GuiltTripCommandSuggester;
import seedu.guilttrip.logic.commands.ListCommand;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.ui.gui.guihandles.CommandBoxHandle;

/**
 * Unit tests for Command Box.
 */
public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private final ArrayList<String> history = new ArrayList<>();
    private ResultDisplayStub resultDisplayStub = new ResultDisplayStub();

    private CommandBoxHandle commandBoxHandle;

    private void suggestCommand(String userInput) {
        resultDisplayStub.setFeedbackToUser(GuiltTripCommandSuggester.getSuggestionString(userInput));
    }

    @BeforeEach
    public void setUp() {
        CommandBox commandBox = new CommandBox(commandText -> {
            history.add(commandText);
            if (commandText.equals(COMMAND_THAT_SUCCEEDS)) {
                return new CommandResult("Command successful");
            }
            throw new CommandException("Command failed");
        }, this::suggestCommand);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartExtension.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    /*@Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();

        // verify that style is changed correctly after multiple successful commands, followed by a failed one
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();

        // verify that style is changed correctly after alternate successful and failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();
    }*/

    /*@Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();

        // verify that style is changed correctly even after alternate successful and failed commands
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }*/

    /*@Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        // verify style of command box does not change after keying normal character
        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());

        // verify style of command box does not change after keying special character
        guiRobot.push(KeyCode.ASTERISK);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());

        // verify style of command box does not change after keying a number
        guiRobot.push(KeyCode.DIGIT5);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }*/

    /**
     * Runs a command that fails, then verifies that <br>
     * - the text remains <br>
     * - the command box's style is the same as
     * {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     * - the text is cleared <br>
     * - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}
