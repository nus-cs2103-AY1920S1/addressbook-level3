package com.typee.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Meeting;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import com.typee.model.person.Person;
import com.typee.model.util.SampleDataUtil;

public class SampleDataUtilTest {

    @Test
    public void getSampleEngagements() {
        Engagement engagementFetched = SampleDataUtil.getSampleEngagements()[0];
        equalsSampleEngagement(engagementFetched);
    }

    @Test
    public void getSampleEngagementList() {
        assertThrows(IndexOutOfBoundsException.class, ()
            -> SampleDataUtil.getSampleEngagementList().getEngagementList().get(1));
        equalsSampleEngagement(SampleDataUtil.getSampleEngagementList().getEngagementList().get(0));
    }

    /**
     * Compares the engagement under test with the expected engagement and throw assertion error if not match.
     *
     * @param engagementFetched engagement under test
     */
    private void equalsSampleEngagement(Engagement engagementFetched) {
        assertDoesNotThrow(() -> assertTrue(engagementFetched instanceof Meeting));
        assertDoesNotThrow(() -> assertEquals(engagementFetched.getAttendees(),
                new AttendeeList(Arrays.asList(new Person[]{new Person(new Name("Uggi"))}))));
        assertDoesNotThrow(() -> assertEquals(engagementFetched.getLocation(),
                new Location("SR-10"), "Test"));
        assertDoesNotThrow(() -> assertEquals(engagementFetched.getPriority(), Priority.HIGH));
    }
}
