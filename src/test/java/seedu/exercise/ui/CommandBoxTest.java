package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.CommonTestData.COMMAND_INPUT_FIELD_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.guihandlers.CommandBoxHandle;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.ui.testutil.GuiUnitTest;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_FAILURE_MESSAGE = "Command failed";
    private static final String COMMAND_SUCCESS_MESSAGE = "Command successful";
    private static final String COMMAND_SUCCESS_INPUT = "success command";
    private static final String COMMAND_FAILURE_INPUT = "failure command";

    private CommandBoxHandle commandBoxHandle;
    private CommandBox commandBox;

    @BeforeEach
    private void setUp() {
        commandBox = new CommandBox(new BasicCommandExecutor());
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                COMMAND_INPUT_FIELD_ID));
        uiPartExtension.setUiPart(commandBox);
    }

    @Test
    public void commandBox_commandEntered_textFieldCleared() {
        commandBoxHandle.run(COMMAND_SUCCESS_INPUT);
        assertEquals("", commandBoxHandle.getInput());
    }

    @Test
    public void commandBox_invalidCommand_styleClassChange() {
        commandBoxHandle.run(COMMAND_FAILURE_INPUT);
        assertTrue(commandBoxHandle.getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS));
    }

    @Test
    public void commandBox_requestFocus_focused() {
        commandBox.requestFocus();
        assertTrue(commandBoxHandle.isFocused());
    }

    private class BasicCommandExecutor implements CommandBox.CommandExecutor {

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            if (commandText.equals(COMMAND_SUCCESS_INPUT)) {
                return new CommandResult(COMMAND_SUCCESS_MESSAGE);
            }
            throw new CommandException(COMMAND_FAILURE_MESSAGE);
        }
    }
}
