package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandHistory.IndexedBasedListIterator;

class CommandHistoryTest {

    private static final List<String> EXPECTED_COMMANDS = List.of(
            "first command",
            "second command",
            "third command"
    );
    private static final int EXPECTED_COMMANDS_LAST_IDX = EXPECTED_COMMANDS.size() - 1;
    private static final String NEW_EXPECTED_COMMAND = "fourth command";

    private CommandHistory emptyCommandHistory() {
        return new CommandHistory();
    }

    private CommandHistory populatedCommandHistory() {
        final CommandHistory commandHistory = new CommandHistory();
        commandHistory.addAll(EXPECTED_COMMANDS);
        return commandHistory;
    }

    @Test
    void addAll_null_throwsNullPointerException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(NullPointerException.class, () -> {
            commandHistory.addAll(null);
        });
    }

    @Test
    void addAll_listOfCommandsToEmptyCommandHistory_success() {
        final CommandHistory commandHistory = emptyCommandHistory();
        commandHistory.addAll(EXPECTED_COMMANDS);

        assertEquals(EXPECTED_COMMANDS, commandHistory);
    }

    @Test
    void getPreviousCommand_emptyCommandHistory_null() {
        final CommandHistory commandHistory = emptyCommandHistory();
        assertNull(commandHistory.getPreviousCommand());
    }

    @Test
    void getPreviousCommand_populatedCommandHistory_mostRecentToLeastRecentCommands() {
        final CommandHistory commandHistory = populatedCommandHistory();
        for (int i = EXPECTED_COMMANDS_LAST_IDX; i >= 0; i--) {
            final String expectedCommand = EXPECTED_COMMANDS.get(i);
            assertEquals(expectedCommand, commandHistory.getPreviousCommand());
        }
    }

    @Test
    void getNextCommand_emptyCommandHistory_null() {
        final CommandHistory commandHistory = emptyCommandHistory();
        assertNull(commandHistory.getNextCommand());
    }

    @Test
    void getNextCommand_populatedCommandHistory_null() {
        final CommandHistory commandHistory = populatedCommandHistory();
        assertNull(commandHistory.getNextCommand());
    }

    @Test
    void getPreviousCommandsThenGetNextCommand_populatedCommandHistory_roundTrip() {
        final CommandHistory commandHistory = populatedCommandHistory();
        for (int i = EXPECTED_COMMANDS_LAST_IDX; i >= 0; i--) {
            final String expectedCommand = EXPECTED_COMMANDS.get(i);
            assertEquals(expectedCommand, commandHistory.getPreviousCommand());
        }

        assertNull(commandHistory.getPreviousCommand());

        /*
        index starts from 1 because the last getPreviousCommand()
        execution in the above loop moves the index to index 0
         */
        for (int i = 1; i < EXPECTED_COMMANDS_LAST_IDX; i++) {
            final String expectedCommand = EXPECTED_COMMANDS.get(i);
            assertEquals(expectedCommand, commandHistory.getNextCommand());
        }
    }

    @Test
    void resetHistoryPointer() {
    }

    @Test
    void add_null_throwsNullPointerException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(NullPointerException.class, () -> {
            commandHistory.add(null);
        });
    }

    @Test
    void add_newCommand_historyPointerWasReset() {
        final CommandHistory commandHistory = populatedCommandHistory();
        assertEquals(EXPECTED_COMMANDS.get(EXPECTED_COMMANDS_LAST_IDX), commandHistory.getPreviousCommand());

        commandHistory.add(NEW_EXPECTED_COMMAND);
        assertEquals(NEW_EXPECTED_COMMAND, commandHistory.getPreviousCommand());
    }

    @Test
    void add_newCommandNavigatedToEarliestCommand_historyPointerWasReset() {
        final CommandHistory commandHistory = populatedCommandHistory();
        while (true) {
            if (commandHistory.getPreviousCommand() == null) {
                break;
            }
        }

        commandHistory.add(NEW_EXPECTED_COMMAND);
        assertEquals(NEW_EXPECTED_COMMAND, commandHistory.getPreviousCommand());
    }

    @Test
    void add_sameNewCommandToBlankCommandHistory_onlyAddedOnce() {
        final CommandHistory commandHistory = emptyCommandHistory();
        commandHistory.add(NEW_EXPECTED_COMMAND);
        Assertions.assertEquals(1, commandHistory.size());
        commandHistory.add(NEW_EXPECTED_COMMAND);
        Assertions.assertEquals(1, commandHistory.size());
    }

    @Test
    void add_nonLastIndexToPopulatedCommandHistory_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.add(0, NEW_EXPECTED_COMMAND);
        });
    }

    @Test
    void add_lastIndexToPopulatedCommandHistory_success() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertDoesNotThrow(() -> {
            commandHistory.add(commandHistory.size(), NEW_EXPECTED_COMMAND);
        });
    }

    @Test
    void listIteratorRemove_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        Assertions.assertThrows(UnsupportedOperationException.class, listIterator::remove);
    }

    @Test
    void listIteratorSet_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            listIterator.set(NEW_EXPECTED_COMMAND);
        });
    }

    @Test
    void listIteratorAdd_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            listIterator.add(NEW_EXPECTED_COMMAND);
        });
    }

    @Test
    void listIteratorHasNext_emptyCommandHistory_false() {
        final CommandHistory commandHistory = emptyCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        assertFalse(listIterator.hasNext());
    }

    @Test
    void listIteratorHasPrevious_emptyCommandHistory_false() {
        final CommandHistory commandHistory = emptyCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        assertFalse(listIterator.hasPrevious());
    }

    @Test
    void listIteratorNext_emptyCommandHistory_throwsNoSuchElementException() {
        final CommandHistory commandHistory = emptyCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        Assertions.assertThrows(NoSuchElementException.class, listIterator::next);
    }

    @Test
    void listIteratorPrevious_emptyCommandHistory_throwsNoSuchElementException() {
        final CommandHistory commandHistory = emptyCommandHistory();
        final ListIterator<String> listIterator = commandHistory.listIterator();

        Assertions.assertThrows(NoSuchElementException.class, listIterator::previous);
    }

    @Test
    void listIteratorRepositionIndex_validIndexWithinPopulatedCommandHistory_success() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final IndexedBasedListIterator customListIterator = (IndexedBasedListIterator) commandHistory.listIterator();

        Assertions.assertDoesNotThrow(() -> {
            customListIterator.repositionIndex(EXPECTED_COMMANDS_LAST_IDX);
        });
    }

    @Test
    void listIteratorRepositionIndex_invalidIndexBeyondPopulatedCommandHistory_throwsIndexOutOfBoundsException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final IndexedBasedListIterator customListIterator = (IndexedBasedListIterator) commandHistory.listIterator();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            customListIterator.repositionIndex(Integer.MAX_VALUE);
        });
    }

    @Test
    void listIteratorRepositionIndex_negativeIndex_throwsIndexOutOfBoundsException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final IndexedBasedListIterator customListIterator = (IndexedBasedListIterator) commandHistory.listIterator();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            customListIterator.repositionIndex(Integer.MIN_VALUE);
        });
    }

    @Test
    void listIteratorRepositionIndex_negativeOneIndex_success() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final IndexedBasedListIterator customListIterator = (IndexedBasedListIterator) commandHistory.listIterator();

        Assertions.assertDoesNotThrow(() -> {
            customListIterator.repositionIndex(-1);
        });
    }

    @Test
    void listIteratorRepositionIndex_sizeOfPopulatedCommandHistory_success() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final IndexedBasedListIterator customListIterator = (IndexedBasedListIterator) commandHistory.listIterator();

        Assertions.assertDoesNotThrow(() -> {
            customListIterator.repositionIndex(commandHistory.size());
        });
    }


    @Test
    void testAdd1() {
    }

    @Test
    void removeRange_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.removeRange(0, 1);
        });
    }

    @Test
    void remove_byIndex_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.remove(0);
        });
    }

    @Test
    void remove_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final String objectToRemove = commandHistory.get(0);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.remove(objectToRemove);
        });
    }

    @Test
    void removeAll_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.removeAll(commandHistory);
        });
    }

    @Test
    void retainAll_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.retainAll(commandHistory);
        });
    }

    @Test
    void removeIf_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        final Predicate<String> alwaysTrue = (unused) -> {
            return true;
        };

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.removeIf(alwaysTrue);
        });
    }

    @Test
    void set_throwsUnsupportedOperationException() {
        final CommandHistory commandHistory = populatedCommandHistory();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            commandHistory.set(0, commandHistory.get(0));
        });
    }
}
