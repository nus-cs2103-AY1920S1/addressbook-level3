package seedu.planner.model.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.planner.model.AccommodationManager;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Itinerary;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Email;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Planner} with sample data.
 */
public class SampleDataUtil {
    public static Accommodation[] getSampleAccommodations() {
        return new Accommodation[]{
            new Accommodation(new Name("Mandarin Orchard Hotel"), new Address("333 Orchard Road"),
                        null, getTagSet("5star")),
            new Accommodation(new Name("Royal Plaza Hotel"), new Address("25 Scotts Road"),
                        null, getTagSet("Epic", "Suite")),
            new Accommodation(new Name("Hard Rock Hotel RWS"), new Address("8 Sentosa Gateway"),
                        null, getTagSet("Resort")),
            new Accommodation(new Name("Hotel 81"), new Address("29 Geylang Road"),
                        null, getTagSet("cheap")),
            new Accommodation(new Name("Tokyo Grand Hotel"), new Address("Shibuya, Tokyo"),
                        null, getTagSet("classmates")),
        };
    }

    public static ReadOnlyAccommodation getSampleAccommodationManager() {
        AccommodationManager sampleAm = new AccommodationManager();
        for (Accommodation sampleAccommodation : getSampleAccommodations()) {
            sampleAm.addAccommodation(sampleAccommodation);
        }
        return sampleAm;
    }

    public static Activity[] getSampleActivities() {
        return new Activity[]{
            new Activity(new Name("Visit Gardens by The Bay"), new Address("18 Marina Gardens Dr"),
                    null, new Cost("23.00"), getTagSet("indoor", "refreshing"),
                    new Duration(90), new Priority(1)),
            new Activity(new Name("Universal Studio Singapore"), new Address("Resort World Sentosa"),
                    null, new Cost("75.00"), getTagSet("unique", "themepark"),
                    new Duration(210), new Priority(2)),
            new Activity(new Name("Night Safari"), new Address("80 Mandai Lake Road"),
                    null, new Cost("44.00"), getTagSet("wild"),
                    new Duration(180), new Priority(3)),
            new Activity(new Name("Singapore City Tour"), new Address("Merlion Park"),
                    null, new Cost("36.00"), getTagSet("bus"),
                    new Duration(210), new Priority(4)),
            new Activity(new Name("Hawker Food Hunt"), new Address("6 Jalan Bukit Merah"),
                    null, null, getTagSet("yummy"), new Duration(60), new Priority(5)),
        };
    }

    public static ReadOnlyActivity getSampleActivityManager() {
        ActivityManager sampleAm = new ActivityManager();
        for (Activity sampleActivity : getSampleActivities()) {
            sampleAm.addActivity(sampleActivity);
        }
        return sampleAm;
    }

    public static Contact[] getSampleContacts() {
        return new Contact[]{
            new Contact(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends")),
            new Contact(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends")),
            new Contact(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours")),
            new Contact(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family")),
            new Contact(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates")),
            new Contact(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyContact getSampleContactManager() {
        ContactManager sampleCm = new ContactManager();
        for (Contact sampleContact : getSampleContacts()) {
            sampleCm.addContact(sampleContact);
        }
        return sampleCm;
    }

    // Need to add more samples
    public static Day[] getSampleDays() {
        Activity [] activities = getSampleActivities();

        ArrayList<ActivityWithTime> sampleActivities1 = new ArrayList<>();
        sampleActivities1.add(new ActivityWithTime(activities[0], LocalTime.of(10, 30)));
        sampleActivities1.add(new ActivityWithTime(activities[1], LocalTime.of(14, 00)));

        ArrayList<ActivityWithTime> sampleActivities2 = new ArrayList<>();
        sampleActivities2.add(new ActivityWithTime(activities[3], LocalTime.of(10, 00)));

        ArrayList<ActivityWithTime> sampleActivities3 = new ArrayList<>();
        sampleActivities3.add(new ActivityWithTime(activities[2], LocalTime.of(18, 30)));
        sampleActivities3.add(new ActivityWithTime(activities[4], LocalTime.of(12, 30)));

        return new Day[]{
            new Day(sampleActivities1), new Day(sampleActivities2), new Day(sampleActivities3)
        };

    }

    public static ReadOnlyItinerary getSampleItinerary() {
        Itinerary sampleItinerary = new Itinerary();
        sampleItinerary.setDays(Arrays.asList(getSampleDays()));
        return sampleItinerary;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
