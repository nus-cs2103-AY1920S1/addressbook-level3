package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.logic.commands.ListPeopleCommand;
import seedu.address.logic.commands.ListPolicyCommand;

class CommandHistoryTest {

    private CommandHistory commandHistory;

    @BeforeEach
    private void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    public void addCommand() {
        commandHistory.addCommand(ListPeopleCommand.COMMAND_WORD, ListPeopleCommand.COMMAND_WORD);
        assertEquals(commandHistory.getHistory().size(), 1);
    }

    @Test
    public void getHistory() {
        commandHistory.addCommand(ListPeopleCommand.COMMAND_WORD, ListPeopleCommand.COMMAND_WORD);
        commandHistory.addCommand(ListPolicyCommand.COMMAND_WORD, ListPolicyCommand.COMMAND_WORD);

        ObservableList<Pair<String, String>> testList = FXCollections.observableArrayList();
        testList.add(new Pair<>(ListPolicyCommand.COMMAND_WORD, ListPolicyCommand.COMMAND_WORD));
        testList.add(new Pair<>(ListPeopleCommand.COMMAND_WORD, ListPeopleCommand.COMMAND_WORD));

        assertEquals(commandHistory.getHistory(), FXCollections.unmodifiableObservableList(testList));
    }

    @Test
    public void equals() {
        // if same object -> returns true
        assertEquals(commandHistory, commandHistory);

        // if different kind of objects -> returns false
        assertNotEquals(commandHistory, new UserPrefs());

        // if objects with same command history -> returns true
        CommandHistory expectedCommandHistory = new CommandHistory();
        expectedCommandHistory.addCommand(ListPeopleCommand.COMMAND_WORD, ListPeopleCommand.COMMAND_WORD);
        commandHistory.addCommand(ListPeopleCommand.COMMAND_WORD, ListPeopleCommand.COMMAND_WORD);
        assertEquals(commandHistory, expectedCommandHistory);

        // objects with different command history -> returns false
        expectedCommandHistory.addCommand("hello", "world");
        assertNotEquals(commandHistory, expectedCommandHistory);
    }
}