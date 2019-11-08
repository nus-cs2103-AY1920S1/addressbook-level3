package com.typee.model;

import static com.typee.testutil.TypicalEngagements.TYPICAL_APPOINTMENT;
import static com.typee.testutil.TypicalEngagements.TYPICAL_INTERVIEW;
import static com.typee.testutil.TypicalEngagements.TYPICAL_MEETING;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.model.engagement.Engagement;
import com.typee.testutil.EngagementBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EngagementListTest {

    private final EngagementList engagementList = new HistoryManager(new EngagementList());

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), engagementList.getEngagementList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> engagementList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEngagementList_replacesData() {
        EngagementList newData = getTypicalEngagementList();
        engagementList.resetData(newData);
        assertEquals(newData, engagementList);
    }

    @Test
    public void resetData_withDuplicateAppointments_noExceptionThrown() {
        // Two engagements with the same details
        Engagement editedAppointment = new EngagementBuilder(TYPICAL_APPOINTMENT).buildAsAppointment();
        List<Engagement> newEngagements = Arrays.asList(TYPICAL_APPOINTMENT, editedAppointment);
        EngagementListStub newData = new EngagementListStub(newEngagements);

        engagementList.resetData(newData);
    }

    @Test
    public void hasEngagement_nullEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> engagementList.hasEngagement(null));
    }

    @Test
    public void hasAppointment_appointmentNotInEngagementList_returnsFalse() {
        assertFalse(engagementList.hasEngagement(TYPICAL_APPOINTMENT));
    }

    @Test
    public void hasInterview_interviewNotInEngagementList_returnsFalse() {
        assertFalse(engagementList.hasEngagement(TYPICAL_INTERVIEW));
    }

    @Test
    public void hasMeeting_meetingNotInEngagementList_returnsFalse() {
        assertFalse(engagementList.hasEngagement(TYPICAL_MEETING));
    }

    @Test
    public void hasAppointment_appointmentInEngagementList_returnsTrue() {
        engagementList.addEngagement(TYPICAL_APPOINTMENT);
        assertTrue(engagementList.hasEngagement(TYPICAL_APPOINTMENT));
    }

    @Test
    public void hasInterview_interviewInEngagementList_returnsTrue() {
        engagementList.addEngagement(TYPICAL_INTERVIEW);
        assertTrue(engagementList.hasEngagement(TYPICAL_INTERVIEW));
    }

    @Test
    public void hasMeeting_meetingInEngagementList_returnsTrue() {
        engagementList.addEngagement(TYPICAL_MEETING);
        assertTrue(engagementList.hasEngagement(TYPICAL_MEETING));
    }

    @Test
    public void hasAppointment_appointmentWithSameDetailsInEngagementList_returnsTrue() {
        engagementList.addEngagement(TYPICAL_APPOINTMENT);
        Engagement editedAppointment = new EngagementBuilder(TYPICAL_APPOINTMENT).buildAsAppointment();
        assertTrue(engagementList.hasEngagement(editedAppointment));
    }

    @Test
    public void hasInterview_interviewWithSameDetailsInEngagementList_returnsTrue() {
        engagementList.addEngagement(TYPICAL_INTERVIEW);
        Engagement editedInterview = new EngagementBuilder(TYPICAL_INTERVIEW).buildAsInterview();
        assertTrue(engagementList.hasEngagement(editedInterview));
    }

    @Test
    public void hasMeeting_meetingWithSameDetailsInEngagementList_returnsTrue() {
        engagementList.addEngagement(TYPICAL_MEETING);
        Engagement editedMeeting = new EngagementBuilder(TYPICAL_MEETING).buildAsMeeting();
        assertTrue(engagementList.hasEngagement(editedMeeting));
    }

    @Test
    public void getEngagementList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> engagementList.getEngagementList().remove(0));
    }

    /**
     * A stub ReadOnlyEngagementList whose engagement list can violate interface constraints.
     */
    private static class EngagementListStub implements ReadOnlyEngagementList {
        private final ObservableList<Engagement> engagements = FXCollections.observableArrayList();

        EngagementListStub(Collection<Engagement> engagements) {
            this.engagements.setAll(engagements);
        }

        @Override
        public ObservableList<Engagement> getEngagementList() {
            return engagements;
        }

        @Override
        public boolean isConflictingEngagement(Engagement engagement) {
            return false;
        }
    }

}
