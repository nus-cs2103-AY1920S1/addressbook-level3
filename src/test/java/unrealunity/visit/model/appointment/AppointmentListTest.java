package unrealunity.visit.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.testutil.Assert;

public class AppointmentListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AppointmentList(null));
    }

    @Test
    public void asUnmodifiableObservableListTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        AppointmentList apptList2 = new AppointmentList(apptTable.getAppointmentList());
        assertTrue(apptList1.asUnmodifiableObservableList().get(0).toString()
                .equals(apptList2.asUnmodifiableObservableList().get(0).toString()));
    }

    @Test
    public void addAppointmentTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        AppointmentList apptList2 = new AppointmentList(apptTable.getAppointmentList());
        apptList1.addAppointment(0, "Tester", 1);
        apptList2.addAppointment(0, "Tester", 1);
        assertTrue(apptList1.asUnmodifiableObservableList().get(1).toString()
                .equals(apptList2.asUnmodifiableObservableList().get(1).toString()));
    }

    @Test
    public void internalAddWithCheckTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        AppointmentList apptList2 = new AppointmentList(apptTable.getAppointmentList());
        apptList1.addAppointment(0, "Test", 3);
        apptList2.addAppointment(0, "Test", 3);
        assertTrue(apptList1.asUnmodifiableObservableList().get(0).toString()
                .equals(apptList2.asUnmodifiableObservableList().get(0).toString()));
        assertEquals(3, apptList2.asUnmodifiableObservableList().get(0).getDays());
    }

    @Test
    public void deleteAppointmentTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        AppointmentList apptList2 = new AppointmentList(apptTable.getAppointmentList());
        apptList1.addAppointment(0, "Tester", 3);
        apptList2.addAppointment(0, "Tester", 3);
        apptList1.deleteAppointment("Tester", -1);
        apptList2.deleteAppointment("Tester", -1);
        assertTrue(apptList1.asUnmodifiableObservableList().size()
                == apptList2.asUnmodifiableObservableList().size());
        assertEquals(1, apptList1.asUnmodifiableObservableList().size());
    }

    @Test
    public void antiDuplicateApptTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        Appointment toCheck1 = new Appointment("[R] Test", 1);
        Appointment toCheck2 = new Appointment("[R] Nope", 1);
        assertFalse(apptList1.antiDuplicate(toCheck1));
        assertTrue(apptList1.antiDuplicate(toCheck2));
    }

    @Test
    public void antiDuplicateDescTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        assertTrue(apptList1.antiDuplicate("[R] Test"));
        assertTrue(apptList1.antiDuplicate("[R] Nope"));
    }

    @Test
    public void sortAppointmentsTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        apptList1.addAppointment(0, "TestA", 5);
        apptList1.addAppointment(0, "TestB", 99);
        apptList1.addAppointment(0, "TestC", 1);
        apptList1.addAppointment(1, "TestX", 5);
        apptList1.addAppointment(1, "TestY", 99);
        apptList1.addAppointment(1, "TestZ", 1);
        apptList1.sortAppointments();
        String expected = "[F] TestY";
        assertEquals(expected, apptList1.asUnmodifiableObservableList().get(2).getDescription());
    }

    @Test
    public void resetAppointmentsTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        AppointmentList apptList1 = new AppointmentList(apptTable.getAppointmentList());
        apptList1.resetAppointments();
        assertEquals(0, apptList1.asUnmodifiableObservableList().size());
    }

}
