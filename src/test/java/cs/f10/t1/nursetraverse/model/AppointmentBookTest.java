package cs.f10.t1.nursetraverse.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.util.CopyUtil;
import cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.appointment.exceptions.DuplicateAppointmentException;
import cs.f10.t1.nursetraverse.testutil.AppointmentBuilder;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentBookTest {


    private final AppointmentBook appointmentBook = new AppointmentBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), appointmentBook.getAppointmentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> appointmentBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAppointmentBook_replacesData() {
        AppointmentBook newData = TypicalAppointments.getTypicalAppointmentBook();
        appointmentBook.resetData(newData);
        assertEquals(newData, appointmentBook);
    }

    @Test
    public void resetData_withDuplicateAppointments_throwsDuplicateAppointmentException() {
        // Two appointments with the same identity fields
        Appointment editedApptOne = new AppointmentBuilder(TypicalAppointments.APPT_ONE)
                .withDescription(CommandTestUtil.DESC_ONE)
                .build();
        List<Appointment> newAppointments = Arrays.asList(TypicalAppointments.APPT_ONE, editedApptOne);
        AppointmentBookTest.AppointmentBookStub newData = new AppointmentBookTest.AppointmentBookStub(newAppointments);

        Assert.assertThrows(DuplicateAppointmentException.class, () -> appointmentBook.resetData(newData));
    }

    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> appointmentBook.hasAppointment(null));
    }

    @Test
    public void hasAppointment_appointmentNotInAppointmentBook_returnsFalse() {
        assertFalse(appointmentBook.hasAppointment(TypicalAppointments.APPT_ONE));
    }

    @Test
    public void hasAppointment_appointmentInAppointmentBook_returnsTrue() {
        appointmentBook.addAppointment(TypicalAppointments.APPT_ONE);
        assertTrue(appointmentBook.hasAppointment(TypicalAppointments.APPT_ONE));
    }

    @Test
    public void hasAppointment_appointmentWithSameIdentityFieldsInAppointmentBook_returnsTrue() {
        appointmentBook.addAppointment(TypicalAppointments.APPT_ONE);
        Appointment editedApptOne = new AppointmentBuilder(TypicalAppointments.APPT_ONE)
                .withDescription(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(appointmentBook.hasAppointment(editedApptOne));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> appointmentBook.getAppointmentList().remove(0));
    }

    @Test
    public void deepCopy() {
        AppointmentBook copy = appointmentBook.deepCopy();

        assertNotSame(copy, appointmentBook);
        assertEquals(copy, appointmentBook);
    }

    @Test
    public void deepCopy_changes_areIndependent() {
        AppointmentBook copy = appointmentBook.deepCopy();
        copy.addAppointment(TypicalAppointments.APPT_ONE);

        assertNotEquals(copy, appointmentBook);
        assertTrue(copy.hasAppointment(TypicalAppointments.APPT_ONE));
        assertFalse(appointmentBook.hasAppointment(TypicalAppointments.APPT_ONE));
    }

    @Test
    public void hashCode_noError() {
        assertDoesNotThrow(appointmentBook::hashCode);

        AppointmentBook typicalAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();
        assertDoesNotThrow(typicalAppointmentBook::hashCode);
    }

    /**
     * A stub ReadOnlyAppointmentBook whose appointments list can violate interface constraints.
     */
    private static class AppointmentBookStub implements ReadOnlyAppointmentBook {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        AppointmentBookStub(Collection<Appointment> appointments) {
            this.appointments.setAll(appointments);
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public AppointmentBookStub deepCopy() {
            return new AppointmentBookStub(CopyUtil.deepCopyOfObservableList(appointments));
        }
    }

}
