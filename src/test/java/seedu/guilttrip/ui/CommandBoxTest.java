/*package seedu.guilttrip.ui;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.ListCommand;
import seedu.guilttrip.logic.commands.exceptions.CommandException;

//import guitests.guihandles.CommandBoxHandle;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private final ArrayList<String> history = new ArrayList<>();

    private CommandBoxHandle commandBoxHandle;

    @BeforeEach
    public void setUp() {
        CommandBox commandBox = new CommandBox(commandText -> {
            history.add(commandText);
            if (commandText.equals(COMMAND_THAT_SUCCEEDS)) {
                return new CommandResult("Command successful");
            }
            throw new CommandException("Command failed");
        }, history);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartExtension.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    *//**
 * Runs a command that fails, then verifies that <br>
 * - the text remains <br>
 * - the command box's style is the same as {@code errorStyleOfCommandBox}.
 * <p>
 * Runs a command that succeeds, then verifies that <br>
 * - the text is cleared <br>
 * - the command box's style is the same as {@code defaultStyleOfCommandBox}.
 * <p>
 * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
 * <p>
 * Runs a command that succeeds, then verifies that <br>
 * - the text is cleared <br>
 * - the command box's style is the same as {@code defaultStyleOfCommandBox}.
 * <p>
 * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
 *//*
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    *//**
 * Runs a command that succeeds, then verifies that <br>
 * - the text is cleared <br>
 * - the command box's style is the same as {@code defaultStyleOfCommandBox}.
 *//*
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    *//**
 * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
 *//*
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}*/
