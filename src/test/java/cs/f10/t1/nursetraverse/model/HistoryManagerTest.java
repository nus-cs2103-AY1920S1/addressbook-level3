//@@author gabrielchao

package cs.f10.t1.nursetraverse.model;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static cs.f10.t1.nursetraverse.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.getTypicalPatientBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.testutil.DummyMutatorCommand;
import javafx.collections.ObservableList;

public class HistoryManagerTest {

    @Test
    public void pushRecord() {
        HistoryManager historyManager = new HistoryManager(1);
        historyManager.pushRecord(new DummyMutatorCommand("1"), new PatientBook(), new AppointmentBook());
        assertTrue(historyManager.size() == 1);
        assertTrue(historyManager.asUnmodifiableObservableList().size() == 1);
    }

    @Test
    public void pushRecord_overMaxSize() {
        HistoryManager historyManager = new HistoryManager(1);
        DummyMutatorCommand targetCommand = new DummyMutatorCommand("2");
        PatientBook targetPatientBook = getTypicalPatientBook();
        AppointmentBook targetAppointmentBook = getTypicalAppointmentBook();

        historyManager.pushRecord(new DummyMutatorCommand("1"), new PatientBook(), new AppointmentBook());
        historyManager.pushRecord(targetCommand, targetPatientBook, targetAppointmentBook);
        ObservableList<HistoryRecord> historyList = historyManager.asUnmodifiableObservableList();

        assertEquals(historyList.get(0).getCommand(), targetCommand);
        assertEquals(historyList.get(0).getCopyOfPatientBook(), targetPatientBook);
        assertTrue(historyList.size() == 1);
    }

    @Test
    public void popRecord_hasRecords() {
        HistoryManager historyManager = new HistoryManager(2);
        DummyMutatorCommand targetCommand = new DummyMutatorCommand("2");
        PatientBook targetPatientBook = getTypicalPatientBook();
        AppointmentBook targetAppointmentBook = getTypicalAppointmentBook();

        historyManager.pushRecord(new DummyMutatorCommand("1"), new PatientBook(), new AppointmentBook());
        historyManager.pushRecord(targetCommand, targetPatientBook, targetAppointmentBook);
        HistoryRecord record = historyManager.popRecord(new PatientBook(), new AppointmentBook()).get();

        assertEquals(record.getCommand(), targetCommand);
        assertEquals(record.getCopyOfPatientBook(), targetPatientBook);
        assertTrue(historyManager.size() == 1);
    }

    @Test
    public void popRecord_noRecords_emptyOptional() {
        HistoryManager historyManager = new HistoryManager(1);

        assertEquals(historyManager.popRecord(new PatientBook(), new AppointmentBook()), Optional.empty());

        historyManager.pushRecord(new DummyMutatorCommand("1"), new PatientBook(), new AppointmentBook());
        historyManager.popRecord(new PatientBook(), new AppointmentBook());

        assertEquals(historyManager.popRecord(new PatientBook(), new AppointmentBook()), Optional.empty());
    }

    @Test
    public void popRecordsTo_hasRecord() {
        HistoryManager historyManager = new HistoryManager(3);
        ObservableList<HistoryRecord> historyList = historyManager.asUnmodifiableObservableList();
        DummyMutatorCommand targetCommand = new DummyMutatorCommand("1");

        historyManager.pushRecord(targetCommand, new PatientBook(), new AppointmentBook());
        historyManager.pushRecord(new DummyMutatorCommand("2"), new PatientBook(), new AppointmentBook());
        historyManager.pushRecord(new DummyMutatorCommand("3"), new PatientBook(), new AppointmentBook());
        historyManager.popRecordsTo(historyList.get(1), new PatientBook(), new AppointmentBook());

        assertEquals(historyList.get(0).getCommand(), targetCommand);
        assertTrue(historyList.size() == 1);
        assertTrue(historyManager.size() == 1);

        // Pop to end of history
        historyManager.popRecordsTo(historyList.get(0), new PatientBook(), new AppointmentBook());

        assertTrue(historyManager.size() == 0);
    }

    @Test
    public void popRecordsTo_noSuchRecord() {
        HistoryRecord outsideRecord = new HistoryRecord(new DummyMutatorCommand("!!!"), new PatientBook(),
                                                        new AppointmentBook());
        HistoryManager historyManager = new HistoryManager(1);

        assertThrows(NoSuchElementException.class, () -> historyManager.popRecordsTo(outsideRecord, new PatientBook(),
                                                                                     new AppointmentBook()));

        historyManager.pushRecord(new DummyMutatorCommand("1"), new PatientBook(), new AppointmentBook());

        assertThrows(NoSuchElementException.class, () -> historyManager.popRecordsTo(outsideRecord, new PatientBook(),
                                                                                     new AppointmentBook()));
        assertTrue(historyManager.size() == 1);
    }

    @Test
    public void popRedo() {
        HistoryManager historyManager = new HistoryManager(1);
        assertEquals(historyManager.popRedo(new PatientBook(), new AppointmentBook()), Optional.empty());

        MutatorCommand commandToRedo = new DummyMutatorCommand("1");
        historyManager.pushRecord(commandToRedo, new PatientBook(), new AppointmentBook());
        historyManager.popRecord(new PatientBook(), new AppointmentBook());
        assertEquals(historyManager.popRedo(new PatientBook(), new AppointmentBook()).get(),
                new HistoryRecord(commandToRedo, new PatientBook(), new AppointmentBook()));
    }
}
