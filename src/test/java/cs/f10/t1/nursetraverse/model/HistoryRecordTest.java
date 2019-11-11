package cs.f10.t1.nursetraverse.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.DummyMutatorCommand;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

/**
 * Contains tests (including interaction with {@code PatientBook}, {@code AppointmentBook}) for {@code HistoryRecord}.
 */
public class HistoryRecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> {
            new HistoryRecord(null, new PatientBook(), new AppointmentBook());
        });
        Assert.assertThrows(NullPointerException.class, () -> {
            new HistoryRecord(new DummyMutatorCommand("1"), null, new AppointmentBook());
        });
        Assert.assertThrows(NullPointerException.class, () -> {
            new HistoryRecord(new DummyMutatorCommand("1"), new PatientBook(), null);
        });
    }

    @Test
    public void modifyOriginalBooks_noChange() {
        PatientBook originalPatientBook = TypicalPatients.getTypicalPatientBook();
        AppointmentBook originalAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();
        HistoryRecord historyRecord = new HistoryRecord(new DummyMutatorCommand("1"),
                originalPatientBook, originalAppointmentBook);

        assertEquals(originalPatientBook, historyRecord.getCopyOfPatientBook());
        originalPatientBook.removePatient(originalPatientBook.getPatientByIndex(Index.fromOneBased(1)));
        assertNotEquals(originalPatientBook, historyRecord.getCopyOfPatientBook());

        assertEquals(originalAppointmentBook, historyRecord.getCopyOfAppointmentBook());
        originalAppointmentBook.removeAppointment(originalAppointmentBook.getAppointmentList().get(0));
        assertNotEquals(originalAppointmentBook, historyRecord.getCopyOfAppointmentBook());
    }

    @Test
    public void equals() {
        MutatorCommand equalMutatorCommand = new DummyMutatorCommand("1");
        PatientBook equalPatientBook = TypicalPatients.getTypicalPatientBook();
        AppointmentBook equalAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();

        HistoryRecord historyRecord1 = new HistoryRecord(equalMutatorCommand, equalPatientBook, equalAppointmentBook);
        HistoryRecord historyRecord2 = new HistoryRecord(equalMutatorCommand, equalPatientBook, equalAppointmentBook);

        assertEquals(historyRecord1, historyRecord2);

        HistoryRecord historyRecord3 = new HistoryRecord(equalMutatorCommand, new PatientBook(), equalAppointmentBook);
        assertNotEquals(historyRecord1, historyRecord3);

        HistoryRecord historyRecord4 = new HistoryRecord(equalMutatorCommand, equalPatientBook, new AppointmentBook());
        assertNotEquals(historyRecord1, historyRecord4);
    }
}
