package com.typee.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.typee.model.EngagementList;
import com.typee.model.HistoryManager;
import com.typee.model.engagement.Engagement;

/**
 * A utility class containing a list of {@code Engagement} objects to be used in tests.
 */
public class TypicalEngagements {

    public static final Engagement TYPICAL_APPOINTMENT = new EngagementBuilder().withDescription("Appointment")
            .withStartAndEndTime(LocalDateTime.of(2019, 10, 20, 10, 0),
                    LocalDateTime.of(2019, 10, 20, 11, 0))
            .buildAsAppointment();
    public static final Engagement TYPICAL_INTERVIEW = new EngagementBuilder().withDescription("Interview")
            .withStartAndEndTime(LocalDateTime.of(2019, 10, 20, 12, 0),
                    LocalDateTime.of(2019, 10, 20, 13, 0))
            .buildAsInterview();
    public static final Engagement TYPICAL_MEETING = new EngagementBuilder().withDescription("Meeting")
            .withStartAndEndTime(LocalDateTime.of(2019, 10, 20, 17, 0),
                    LocalDateTime.of(2019, 10, 20, 21, 0))
            .buildAsMeeting();
    public static final Engagement LUNCH_APPOINTMENT = new EngagementBuilder().withDescription("Lunch")
            .withStartAndEndTime(LocalDateTime.of(2019, 11, 20, 10, 0),
                    LocalDateTime.of(2019, 11, 20, 11, 0))
            .buildAsAppointment();
    public static final Engagement GOOGLE_INTERVIEW = new EngagementBuilder().withDescription("Googs")
            .withStartAndEndTime(LocalDateTime.of(2019, 11, 20, 12, 0),
                    LocalDateTime.of(2019, 11, 20, 13, 0))
            .buildAsInterview();
    public static final Engagement TEAM_MEETING = new EngagementBuilder().withDescription("Team Project Meeting")
            .withStartAndEndTime(LocalDateTime.of(2019, 11, 20, 17, 0),
                    LocalDateTime.of(2019, 11, 20, 21, 0))
            .buildAsMeeting();

    private TypicalEngagements() {
    } // prevents instantiation

    /**
     * Returns an {@code EngagementList} with all the typical persons.
     */
    public static HistoryManager getTypicalEngagementList() {
        EngagementList engagementList = new EngagementList();
        for (Engagement engagement : getTypicalEngagements()) {
            engagementList.addEngagement(engagement);
        }
        HistoryManager hm = new HistoryManager(engagementList);
        return hm;
    }

    public static List<Engagement> getTypicalEngagements() {
        return new ArrayList<>(Arrays.asList(TYPICAL_APPOINTMENT, TYPICAL_INTERVIEW, TYPICAL_MEETING));
    }

}
