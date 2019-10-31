package com.typee.model.person;

import static com.typee.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TEAM_MEETING;
import static com.typee.testutil.TypicalEngagements.TEAM_MEETING;
import static com.typee.testutil.TypicalEngagements.TYPICAL_MEETING;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.model.engagement.Engagement;
import com.typee.testutil.EngagementBuilder;

public class MeetingTest {

    @Test
    public void isSameMeeting() {
        // same object -> returns true
        assertTrue(TYPICAL_MEETING.isSameEngagement(TYPICAL_MEETING));

        // null -> returns false
        assertFalse(TYPICAL_MEETING.isSameEngagement(null));

        // different description -> returns false
        Engagement editedMeeting = new EngagementBuilder(TYPICAL_MEETING)
                .withDescription(VALID_DESCRIPTION_TEAM_MEETING)
                .buildAsMeeting();
        assertFalse(TYPICAL_MEETING.isSameEngagement(editedMeeting));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Engagement meetingCopy = new EngagementBuilder(TYPICAL_MEETING).buildAsMeeting();
        assertTrue(TYPICAL_MEETING.equals(meetingCopy));

        // same object -> returns true
        assertTrue(TYPICAL_MEETING.equals(TYPICAL_MEETING));

        // null -> returns false
        assertFalse(TYPICAL_MEETING.equals(null));

        // different type -> returns false
        assertFalse(TYPICAL_MEETING.equals(5));

        // different meeting -> returns false
        assertFalse(TYPICAL_MEETING.equals(TEAM_MEETING));

        // different description -> returns false
        Engagement editedMeeting = new EngagementBuilder(TYPICAL_MEETING)
                .withDescription(VALID_DESCRIPTION_TEAM_MEETING)
                .buildAsMeeting();
        assertFalse(TYPICAL_MEETING.equals(editedMeeting));
    }

}
