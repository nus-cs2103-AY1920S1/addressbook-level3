package unrealunity.visit.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.testutil.Assert;

public class AppointmentTableTest {

    @Test
    public void getDefaultAppointmentsTest() {
        AppointmentTable apptTable = AppointmentTable.getDefaultAppointments();
        String expected = "Reminders:\nNo reminders found.\n\nFollow-ups:\nNo follow-ups found.\n";
        assertEquals(expected, apptTable.outputAppointments());
    }

    @Test
    public void addAppointmentTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        Assert.assertThrows(CommandException.class, () -> {
            apptTable.addAppointment(0, "Test", 1);
        });

        apptTable.addAppointment(0, "Tester", 1);
        String expected = "Reminders:\nTester: for 1 days\nTest: for 1 days"
            + "\n\nFollow-ups:\nNo follow-ups found.\n";
        assertEquals(expected, apptTable.outputAppointments());
    }

    @Test
    public void deleteAppointmentTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        apptTable.addAppointment(0, "Tester", 1);
        apptTable.deleteAppointment("Test", 1);
        String expected = "Reminders:\nTester: for 1 days"
                + "\n\nFollow-ups:\nNo follow-ups found.\n";
        assertEquals(expected, apptTable.outputAppointments());
        apptTable.deleteAppointment("Tester", -1);
        String expected2 = "Reminders:\nNo reminders found."
                + "\n\nFollow-ups:\nNo follow-ups found.\n";
        assertEquals(expected2, apptTable.outputAppointments());
    }

    @Test
    public void sortAppointmentsTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "TestA", 5);
        apptTable.addAppointment(0, "TestB", 99);
        apptTable.addAppointment(0, "TestC", 1);
        apptTable.addAppointment(1, "TestX", 5);
        apptTable.addAppointment(1, "TestY", 99);
        apptTable.addAppointment(1, "TestZ", 1);
        apptTable.sortAppointments();
        String expected = "Reminders:\nTestC: for 1 days\nTestA: for 5 days\n"
            + "TestB: for 99 days\n\nFollow-ups:\nTestZ: in 1 days\nTestX: in 5 days\n"
            + "TestY: in 99 days\n";
        assertEquals(expected, apptTable.outputAppointments());
    }

    @Test
    public void cascadeDayTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "TestA", 5);
        apptTable.addAppointment(0, "TestB", 99);
        apptTable.addAppointment(0, "TestC", 1);
        apptTable.addAppointment(1, "TestX", 5);
        apptTable.addAppointment(1, "TestY", 99);
        apptTable.addAppointment(1, "TestZ", 1);
        apptTable.cascadeDay(3);
        String expected = "Reminders:\nTestA: for 2 days\nTestB: for 96 days\n"
                + "\nFollow-ups:\nTestY: in 96 days\nTestX: in 2 days\n";
        assertEquals(expected, apptTable.outputAppointments());
    }

    @Test
    public void outputAppointmentsTest() throws CommandException {
        AppointmentTable apptTable = new AppointmentTable();
        apptTable.addAppointment(0, "Test", 1);
        String expected = "Reminders:\nTest: for 1 days\n\nFollow-ups:\nNo follow-ups found.\n";
        assertEquals(expected, apptTable.outputAppointments());
    }

}
