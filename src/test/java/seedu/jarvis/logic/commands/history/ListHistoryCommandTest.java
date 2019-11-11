package seedu.jarvis.logic.commands.history;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.testutil.CommandStub;
import seedu.jarvis.testutil.ModelStub;

/**
 * Tests the behaviour of {@code ListHistoryCommand}.
 */
public class ListHistoryCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void hasInverseExecution() {
        ListHistoryCommand listHistoryCommand = new ListHistoryCommand();
        assertFalse(listHistoryCommand.hasInverseExecution());
    }

    @Test
    public void executeInverse_throwsCommandException() {
        ListHistoryCommand listHistoryCommand = new ListHistoryCommand();
        assertThrows(CommandException.class, ListHistoryCommand.MESSAGE_NO_INVERSE, () ->
                listHistoryCommand.executeInverse(model));
    }

    @Test
    public void execute_noCommands_success() {
        ListHistoryCommand listHistoryCommand = new ListHistoryCommand();
        assertCommandSuccess(listHistoryCommand, model, String.format(ListHistoryCommand.MESSAGE_SUCCESS,
                model.getAvailableNumberOfExecutedCommands(), model.getAvailableNumberOfInverselyExecutedCommands()),
                model);
    }

    @Test
    public void execute_withCommands_success() {
        ListHistoryCommand listHistoryCommand = new ListHistoryCommand();
        model.rememberExecutedCommand(new CommandStub());
        model.rememberExecutedCommand(new CommandStub());
        model.rollback();
        assertCommandSuccess(listHistoryCommand, model, String.format(ListHistoryCommand.MESSAGE_SUCCESS,
                model.getAvailableNumberOfExecutedCommands(), model.getAvailableNumberOfInverselyExecutedCommands()),
                model);
    }

    /**
     * Model stub class that can remember executed commands and return the number of commands
     * stored as executed commands and inversely executed commands. This stub class can also rollback
     * and commit commands, however, no commands
     */
    private static class ModelStubRemembersCommands extends ModelStub {
        private List<Command> executedCommands = new ArrayList<>();
        private List<Command> inverselyExecutedCommands = new ArrayList<>();

        @Override
        public void rememberExecutedCommand(Command command) {
            executedCommands.add(command);
        }

        @Override
        public int getAvailableNumberOfExecutedCommands() {
            return executedCommands.size();
        }

        @Override
        public int getAvailableNumberOfInverselyExecutedCommands() {
            return inverselyExecutedCommands.size();
        }

        @Override
        public boolean rollback() {
            if (executedCommands.isEmpty()) {
                return false;
            }
            return inverselyExecutedCommands.add(executedCommands.remove(executedCommands.size() - 1));
        }

        @Override
        public boolean commit() {
            if (inverselyExecutedCommands.isEmpty()) {
                return false;
            }
            return executedCommands.add(inverselyExecutedCommands.remove(inverselyExecutedCommands.size() - 1));
        }
    }
}
