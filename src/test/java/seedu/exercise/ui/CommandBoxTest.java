package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private CommandBoxHandle commandBoxHandle;

    @BeforeEach
    private void setUp() {
        CommandBox commandBox = new CommandBox(new BasicCommandExecutor());
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartExtension.setUiPart(commandBox);
    }

    @Test
    public void commandBox_commandEntered_textFieldCleared() {
        commandBoxHandle.run(COMMAND_SUCCESS_INPUT);
        assertEquals("", commandBoxHandle.getInput());
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
