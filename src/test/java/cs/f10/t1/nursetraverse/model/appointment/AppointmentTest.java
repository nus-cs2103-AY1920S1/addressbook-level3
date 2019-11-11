package cs.f10.t1.nursetraverse.model.appointment;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.DESC_ONE;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.END_DATE_TIME_ONE;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.FREQUENCY_ONE;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.START_DATE_TIME_ONE;
import static cs.f10.t1.nursetraverse.testutil.TypicalAppointments.APPT_ONE;
import static cs.f10.t1.nursetraverse.testutil.TypicalAppointments.APPT_TWO;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.GEORGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.testutil.AppointmentBuilder;

public class AppointmentTest {

    @Test
    public void equals() {
        // same values -> returns true
        Appointment apptOneCopy = new AppointmentBuilder(APPT_ONE).build();
        assertTrue(APPT_ONE.equals(apptOneCopy));

        // same object -> returns true
        assertTrue(APPT_ONE.equals(APPT_ONE));

        // null -> returns false
        assertFalse(APPT_ONE.equals(null));

        // different type -> returns false
        assertFalse(APPT_ONE.equals(5));

        // different appointment -> returns false
        assertFalse(APPT_ONE.equals(APPT_TWO));

        // different start date time -> returns false
        Appointment editedApptOne = new AppointmentBuilder(APPT_ONE).withStartDateTime(START_DATE_TIME_ONE).build();
        assertFalse(APPT_ONE.equals(editedApptOne));

        // different end date time -> returns false
        editedApptOne = new AppointmentBuilder(APPT_ONE).withEndDateTime(END_DATE_TIME_ONE).build();
        assertFalse(APPT_ONE.equals(editedApptOne));

        // different email -> returns false
        editedApptOne = new AppointmentBuilder(APPT_ONE).withFrequency(FREQUENCY_ONE).build();
        assertFalse(APPT_ONE.equals(editedApptOne));

        // different patient -> returns false
        editedApptOne = new AppointmentBuilder(APPT_ONE).withPatient(GEORGE).build();
        assertFalse(APPT_ONE.equals(editedApptOne));

        // different tags -> returns false
        editedApptOne = new AppointmentBuilder(APPT_ONE).withDescription(DESC_ONE).build();
        assertFalse(APPT_ONE.equals(editedApptOne));
    }
}
