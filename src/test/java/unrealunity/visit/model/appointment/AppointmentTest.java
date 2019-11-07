package unrealunity.visit.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.testutil.Assert;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null, 0));
    }

    @Test
    public void getDescriptionTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        assertEquals(appt1.getDescription(), appt2.getDescription());
        assertEquals(testDesc, appt1.getDescription());
    }

    @Test
    public void getDescriptionRawTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        assertEquals(appt1.getDescriptionRaw(), appt2.getDescriptionRaw());
        assertEquals(testDesc.substring(4), appt1.getDescriptionRaw());
    }

    @Test
    public void getTypeTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        assertEquals(appt1.getType(), appt2.getType());
        assertEquals(testDesc.substring(0, 3), appt1.getType());
    }

    @Test
    public void getDaysTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        assertEquals(appt1.getDays(), appt2.getDays());
        assertEquals(1, appt1.getDays());
    }

    @Test
    public void setDaysTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        appt1.setDays(2);
        appt2.setDays(2);
        assertEquals(appt1.getDays(), appt2.getDays());
        assertEquals(2, appt1.getDays());
    }

    @Test
    public void getDaysStringTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        assertEquals(appt1.getDaysString(), appt2.getDaysString());
        assertEquals("1", appt1.getDaysString());
    }

    @Test
    public void isSameAppointmentApptTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc, 1);
        Appointment appt3 = new Appointment(testDesc, 2);
        assertTrue(appt1.isSameAppointment((appt2)));
        assertFalse(appt1.isSameAppointment((appt3)));
    }

    @Test
    public void isSameAppointmentDescTest() {
        String testDesc = "[R] test";
        Appointment appt1 = new Appointment(testDesc, 1);
        Appointment appt2 = new Appointment(testDesc + "a", 2);
        assertTrue(appt1.isSameAppointment((testDesc.substring(4))));
        assertFalse(appt2.isSameAppointment((testDesc.substring(4))));
    }

}
