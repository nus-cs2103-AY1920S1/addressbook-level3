package com.typee.model.util;

import com.typee.model.AddressBook;
import com.typee.model.ReadOnlyAddressBook;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import com.typee.model.person.Person;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Engagement[] getSampleEngagements() {
        return new Engagement[] {
                Engagement.of(EngagementType.MEETING, LocalDateTime.now(), LocalDateTime.now(),
                        new AttendeeList(Arrays.asList(new Person[]{new Person(new Name("Uggi"))})),
                        new Location("SR-10"), "Test", Priority.HIGH)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Engagement sampleEngagement : getSampleEngagements()) {
            sampleAb.addEngagement(sampleEngagement);
        }
        return sampleAb;
    }
}
