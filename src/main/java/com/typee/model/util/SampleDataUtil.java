package com.typee.model.util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import com.typee.model.EngagementList;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.engagement.TimeSlot;
import com.typee.model.engagement.exceptions.InvalidTimeException;
import com.typee.model.person.Name;
import com.typee.model.person.Person;

/**
 * Contains utility methods for populating {@code EngagementList} with sample data.
 */
public class SampleDataUtil {
    public static Engagement[] getSampleEngagements() {
        try {
            return new Engagement[]{
                    Engagement.of(EngagementType.MEETING,
                            new TimeSlot(
                                    LocalDateTime.of(2019, Month.NOVEMBER, 12, 15, 0),
                                    LocalDateTime.of(2019, Month.NOVEMBER, 12, 17, 0)),
                            new AttendeeList(Arrays.asList(new Person[]{new Person(new Name("Uggi"))})),
                            new Location("SR-10"), "Test", Priority.HIGH)
            };
        } catch (InvalidTimeException e) {
            return new Engagement[] {};
        }
    }

    public static ReadOnlyEngagementList getSampleEngagementList() {
        EngagementList sampleEngagementList = new EngagementList();
        for (Engagement sampleEngagement : getSampleEngagements()) {
            sampleEngagementList.addEngagement(sampleEngagement);
        }
        return sampleEngagementList;
    }
}
