package unrealunity.visit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.testutil.Assert;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void setAppointmentsTable_nullAppointmentsTable_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPref.setAppointmentsTable(null));
    }

    @Test
    public void getAppointmentListTest() {
        UserPrefs userPref = new UserPrefs();
        UserPrefs userPref2 = new UserPrefs();
        assertEquals(userPref.getAppointmentList(), userPref2.getAppointmentList());
    }

    @Test
    public void addAppointmentTest() throws CommandException {
        UserPrefs userPref = new UserPrefs();
        UserPrefs userPref2 = new UserPrefs();
        int type = 0;
        String description = "test";
        int days = 7;
        userPref.addAppointment(type, description, days);
        userPref2.addAppointment(type, description, days);
        assertEquals(userPref.outputAppointments(), userPref2.outputAppointments());
    }

    @Test
    public void sortAppointmentsTest() throws CommandException {
        UserPrefs userPref = new UserPrefs();
        userPref.addAppointment(0, "TestA", 5);
        userPref.addAppointment(0, "TestB", 99);
        userPref.addAppointment(0, "TestC", 1);
        userPref.addAppointment(1, "TestX", 5);
        userPref.addAppointment(1, "TestY", 99);
        userPref.addAppointment(1, "TestZ", 1);
        userPref.sortAppointments();
        String expected = "Reminders:\nTestC: for 1 days\nTestA: for 5 days\nTestB: for 99 days"
                + "\n\nFollow-ups:\nTestZ: in 1 days\nTestX: in 5 days\nTestY: in 99 days\n";
        assertEquals(userPref.outputAppointments(), expected);
    }

    @Test
    public void outputAppointmentsTest() throws CommandException {
        UserPrefs userPref = new UserPrefs();
        userPref.addAppointment(0, "TestA", 5);
        userPref.addAppointment(0, "TestB", 99);
        userPref.addAppointment(0, "TestC", 1);
        userPref.addAppointment(1, "TestX", 5);
        userPref.addAppointment(1, "TestY", 99);
        userPref.addAppointment(1, "TestZ", 1);
        String expected = "Reminders:\nTestA: for 5 days\nTestC: for 1 days\nTestB: for 99 days"
                + "\n\nFollow-ups:\nTestZ: in 1 days\nTestY: in 99 days\nTestX: in 5 days\n";
        assertEquals(userPref.outputAppointments(), expected);
    }

    @Test
    public void resetAppointmentsTest() throws CommandException {
        UserPrefs userPref = new UserPrefs();
        userPref.addAppointment(0, "TestA", 5);
        userPref.addAppointment(0, "TestB", 99);
        userPref.addAppointment(0, "TestC", 1);
        userPref.addAppointment(1, "TestX", 5);
        userPref.addAppointment(1, "TestY", 99);
        userPref.addAppointment(1, "TestZ", 1);
        userPref.resetAppointments();
        assertEquals(userPref.getAppointmentTable(), new UserPrefs().getAppointmentTable());
    }

    @Test
    public void getLastUpdateTest() {
        UserPrefs userPref = new UserPrefs();
        assertEquals(userPref.getLastUpdate(), LocalDate.now());
    }

}
