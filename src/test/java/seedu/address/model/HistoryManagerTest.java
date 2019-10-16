package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;

public class HistoryManagerTest {

    @Test
    public void pushRecord() {
        HistoryManager historyManager = new HistoryManager(1);
        historyManager.pushRecord(new DummyMutatorCommand("1"), new AddressBook());
        assertTrue(historyManager.size() == 1);
        assertTrue(historyManager.asUnmodifiableObservableList().size() == 1);
    }

    @Test
    public void pushRecord_overMaxSize() {
        HistoryManager historyManager = new HistoryManager(1);
        DummyMutatorCommand targetCommand = new DummyMutatorCommand("2");
        AddressBook targetAddressBook = getTypicalAddressBook();

        historyManager.pushRecord(new DummyMutatorCommand("1"), new AddressBook());
        historyManager.pushRecord(targetCommand, targetAddressBook);
        ObservableList<HistoryRecord> historyList = historyManager.asUnmodifiableObservableList();

        assertEquals(historyList.get(0).getCommand(), targetCommand);
        assertEquals(historyList.get(0).getCopyOfAddressBook(), targetAddressBook);
        assertTrue(historyList.size() == 1);
    }

    @Test
    public void popRecord_hasRecords() {
        HistoryManager historyManager = new HistoryManager(2);
        DummyMutatorCommand targetCommand = new DummyMutatorCommand("2");
        AddressBook targetAddressBook = getTypicalAddressBook();

        historyManager.pushRecord(new DummyMutatorCommand("1"), new AddressBook());
        historyManager.pushRecord(targetCommand, targetAddressBook);
        HistoryRecord record = historyManager.popRecord().get();

        assertEquals(record.getCommand(), targetCommand);
        assertEquals(record.getCopyOfAddressBook(), targetAddressBook);
        assertTrue(historyManager.size() == 1);
    }

    @Test
    public void popRecord_noRecords_emptyOptional() {
        HistoryManager historyManager = new HistoryManager(1);

        assertEquals(historyManager.popRecord(), Optional.empty());

        historyManager.pushRecord(new DummyMutatorCommand("1"), new AddressBook());
        historyManager.popRecord();

        assertEquals(historyManager.popRecord(), Optional.empty());
    }

    @Test
    public void popRecordsTo_hasRecord() {
        HistoryManager historyManager = new HistoryManager(3);
        ObservableList<HistoryRecord> historyList = historyManager.asUnmodifiableObservableList();
        DummyMutatorCommand targetCommand = new DummyMutatorCommand("1");

        historyManager.pushRecord(targetCommand, new AddressBook());
        historyManager.pushRecord(new DummyMutatorCommand("2"), new AddressBook());
        historyManager.pushRecord(new DummyMutatorCommand("3"), new AddressBook());
        historyManager.popRecordsTo(historyList.get(1));

        assertEquals(historyList.get(0).getCommand(), targetCommand);
        assertTrue(historyList.size() == 1);
        assertTrue(historyManager.size() == 1);

        // Pop to end of history
        historyManager.popRecordsTo(historyList.get(0));

        assertTrue(historyManager.size() == 0);
    }

    @Test
    public void popRecordsTo_noSuchRecord() {
        HistoryRecord outsideRecord = new HistoryRecord(new DummyMutatorCommand("!!!"), new AddressBook());
        HistoryManager historyManager = new HistoryManager(1);

        assertThrows(NoSuchElementException.class, () -> historyManager.popRecordsTo(outsideRecord));

        historyManager.pushRecord(new DummyMutatorCommand("1"), new AddressBook());

        assertThrows(NoSuchElementException.class, () -> historyManager.popRecordsTo(outsideRecord));
        assertTrue(historyManager.size() == 1);
    }

    private static class DummyMutatorCommand extends Command implements MutatorCommand {
        private String dummyData;

        public DummyMutatorCommand(String dummyData) {
            this.dummyData = dummyData;
        }

        public CommandResult execute(Model model) {
            return new CommandResult("This is a dummy command with data " + dummyData);
        }
    }
}
