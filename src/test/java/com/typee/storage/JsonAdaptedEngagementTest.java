package com.typee.storage;

import static com.typee.testutil.TypicalEngagements.TEAM_MEETING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.typee.commons.exceptions.IllegalValueException;

class JsonAdaptedEngagementTest {

    private static final String INVALID_ENGAGEMENT_TYPE = "What is this?";
    private static final String INVALID_TIME_SLOT = TEAM_MEETING.getTimeSlot().toString() + "hehe";
    private static final String INVALID_LOCATION = "    ";
    private static final String INVALID_DESCRIPTION = "  ";
    private static final String INVALID_ATTENDEES = "YO|||";
    private static final String INVALID_PRIORITY = "priority?!";

    private static final String VALID_ENGAGEMENT_TYPE = "meeting";
    private static final String VALID_TIME_SLOT = TEAM_MEETING.getTimeSlot().toString();
    private static final String VALID_LOCATION = "I3-AUD";
    private static final String VALID_DESCRIPTION = "CS2103T Lecture";
    private static final String VALID_ATTENDEES = "Damith | And His | Boys";
    private static final String VALID_PRIORITY = "HIGH";


    @Test
    void toModelType_validEngagement_returnsEngagement() throws IllegalValueException {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(TEAM_MEETING);
        assertEquals(TEAM_MEETING, jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_invalidEngagementType_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(INVALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_nullEngagementType_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(null,
                VALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_invalidTimeSlot_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                INVALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_nullTimeSlot_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                null, VALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, INVALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, null, VALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, INVALID_DESCRIPTION, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, null, VALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_invalidAttendees_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, INVALID_ATTENDEES, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_nullAttendees_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, null, VALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, INVALID_PRIORITY);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

    @Test
    void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedEngagement jsonAdaptedEngagement = new JsonAdaptedEngagement(VALID_ENGAGEMENT_TYPE,
                VALID_TIME_SLOT, VALID_LOCATION, VALID_DESCRIPTION, VALID_ATTENDEES, null);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEngagement.toModelType());
    }

}

