package io.xpire.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.Command;
import io.xpire.logic.commands.CommandResult;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.state.StateManager;

public class UndoableHistoryManagerManagerTest {

    private UndoableHistoryManager<CommandStub> historyManager = new UndoableHistoryManager<>();

    @BeforeEach
    public void initialise() {
        IntStream.rangeClosed(1, 5).forEach(i -> historyManager.save(new CommandStub(i)));
    }

    @Test
    public void save_correctElementSaved() {
        historyManager.save(new CommandStub(100));
        assertEquals(historyManager.previous(), new CommandStub(100));
    }

    @Test
    public void previous_returnsCorrectElement() {
        assertEquals(historyManager.previous(), new CommandStub(5));
        assertEquals(historyManager.previous(), new CommandStub(4));
        IntStream.rangeClosed(1, 3).forEach(i -> historyManager.previous());
        assertThrows(AssertionError.class, () -> historyManager.previous());
    }

    @Test
    public void next_returnsCorrectElement() {
        assertEquals(historyManager.next(), new CommandStub(5));
        historyManager.previous();
        historyManager.previous();
        assertEquals(historyManager.next(), new CommandStub(4));
        assertEquals(historyManager.next(), new CommandStub(5));
    }


    private class CommandStub extends Command {

        private int index;

        CommandStub(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof CommandStub)) {
                return false;
            } else {
                CommandStub other = (CommandStub) obj;
                return this.index == other.getIndex();
            }
        }

        @Override
        public CommandResult execute(Model model, StateManager stackManager) throws CommandException, ParseException {
            throw new AssertionError("This method in CommandStub is not meant to be called");
        }
    }
}
