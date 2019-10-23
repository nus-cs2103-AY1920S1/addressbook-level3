package com.typee.model.person;

import static com.typee.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LUNCH_APPOINTMENT;
import static com.typee.testutil.TypicalEngagements.LUNCH_APPOINTMENT;
import static com.typee.testutil.TypicalEngagements.TYPICAL_APPOINTMENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.model.engagement.Engagement;
import com.typee.testutil.EngagementBuilder;

public class AppointmentTest {

    @Test
    public void isSameAppointment() {
        // same object -> returns true
        assertTrue(TYPICAL_APPOINTMENT.isSameEngagement(TYPICAL_APPOINTMENT));

        // null -> returns false
        assertFalse(TYPICAL_APPOINTMENT.isSameEngagement(null));

        // different description -> returns false
        Engagement editedAppointment = new EngagementBuilder(TYPICAL_APPOINTMENT)
                .withDescription(VALID_DESCRIPTION_LUNCH_APPOINTMENT)
                .buildAsAppointment();
        assertFalse(TYPICAL_APPOINTMENT.isSameEngagement(editedAppointment));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Engagement appointmentCopy = new EngagementBuilder(TYPICAL_APPOINTMENT).buildAsAppointment();
        assertTrue(TYPICAL_APPOINTMENT.equals(appointmentCopy));

        // same object -> returns true
        assertTrue(TYPICAL_APPOINTMENT.equals(TYPICAL_APPOINTMENT));

        // null -> returns false
        assertFalse(TYPICAL_APPOINTMENT.equals(null));

        // different type -> returns false
        assertFalse(TYPICAL_APPOINTMENT.equals(5));

        // different appointment -> returns false
        assertFalse(TYPICAL_APPOINTMENT.equals(LUNCH_APPOINTMENT));

        // different description -> returns false
        Engagement editedAppointment = new EngagementBuilder(TYPICAL_APPOINTMENT)
                .withDescription(VALID_DESCRIPTION_LUNCH_APPOINTMENT)
                .buildAsAppointment();
        assertFalse(TYPICAL_APPOINTMENT.equals(editedAppointment));
    }

}
