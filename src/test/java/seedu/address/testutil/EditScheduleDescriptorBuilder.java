package seedu.address.testutil;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditScheduleDescriptor objects.
 */
public class EditScheduleDescriptorBuilder {

    private EditScheduleDescriptor descriptor;

    public EditScheduleDescriptorBuilder() {
        descriptor = new EditScheduleDescriptor();
    }

    public EditScheduleDescriptorBuilder(EditScheduleDescriptor descriptor) {
        this.descriptor = new EditScheduleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditScheduleDescriptor} with fields containing {@code schedule}'s details
     */
    public EditScheduleDescriptorBuilder(Schedule schedule) {
        descriptor = new EditScheduleDescriptor();
        Calendar newDate = new Calendar.Builder().build();
        newDate.set(Calendar.YEAR, schedule.getCalendar().get(Calendar.YEAR));
        newDate.set(Calendar.MONTH, schedule.getCalendar().get(Calendar.MONTH));
        newDate.set(Calendar.DAY_OF_MONTH, schedule.getCalendar().get(Calendar.DAY_OF_MONTH));
        descriptor.setDate(newDate);

        Calendar newTime = new Calendar.Builder().build();
        newTime.set(Calendar.HOUR_OF_DAY, schedule.getCalendar().get(Calendar.HOUR_OF_DAY));
        newTime.set(Calendar.MINUTE, schedule.getCalendar().get(Calendar.MINUTE));
        descriptor.setTime(newTime);

        descriptor.setVenue(schedule.getVenue());
        descriptor.setTags(schedule.getTags());
    }

    /**
     * Sets the {@code Date} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar newCalendar = new Calendar.Builder().setDate(year, month, date).build();
        descriptor.setDate(newCalendar);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        Calendar newCalendar = new Calendar.Builder().setTimeOfDay(hour, min, 0).build();
        descriptor.setTime(newCalendar);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditScheduleDescriptor}
     * that we are building.
     */
    public EditScheduleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditScheduleDescriptor build() {
        return descriptor;
    }

}
