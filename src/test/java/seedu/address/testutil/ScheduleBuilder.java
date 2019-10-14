package seedu.address.testutil;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Schedule objects.
 */
public class ScheduleBuilder {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_VENUE = "Central Library";
    private static final Calendar DEFAULT_CALENDAR = new Calendar.Builder()
            .setDate(2019, 12, 1).setTimeOfDay(23, 30, 0).build();

    private UUID id;
    private Calendar calendar;
    private Venue venue;
    private Set<Tag> tags;

    public ScheduleBuilder() {
        id = DEFAULT_ID;
        calendar = DEFAULT_CALENDAR;
        venue = new Venue(DEFAULT_VENUE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ScheduleBuilder with the data of {@code scheduleToCopy}.
     */
    public ScheduleBuilder(Schedule scheduleToCopy) {
        id = scheduleToCopy.getId();
        calendar = scheduleToCopy.getCalendar();
        venue = scheduleToCopy.getVenue();
        tags = new HashSet<>(scheduleToCopy.getTags());
    }

    /**
     * Sets the {@code UUID} of the {@code Order} that we are building.
     */
    public ScheduleBuilder withId(UUID id) {
        this.id = id;
        return this;
    }


    /**
     * Sets the {@code Calendar} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withCalendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Schedule build() {
        return new Schedule(id, calendar, venue, tags);
    }

}
