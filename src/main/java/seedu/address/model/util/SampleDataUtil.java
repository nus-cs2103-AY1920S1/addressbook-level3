package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.DateTime;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Event;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.record.BloodSugarBuilder;
import seedu.address.model.record.BmiBuilder;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.tag.Tag;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodBuilder;
import seedu.sgm.model.food.UniqueFoodList;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Food[] getSampleFoods() {
        return new Food[]{
        new FoodBuilder().build(),
        new FoodBuilder().withFoodName("Potatodfafadfafadf").withFoodType("sv").build(),
        new FoodBuilder().withFoodName("Banana").withFoodType("f").build(),
        new FoodBuilder().withFoodName("Chicken").withFoodType("p").build(),
        new FoodBuilder().withFoodName("Potato chips").withFoodType("s").build(),
        new FoodBuilder().withFoodName("Chicken Rice").withFoodType("m").build(),
        new FoodBuilder().withFoodName("Broccoli2").build(),
        new FoodBuilder().withFoodName("Potato2").withFoodType("sv").build(),
        new FoodBuilder().withFoodName("Banana2").withFoodType("f").build(),
        new FoodBuilder().withFoodName("Chicken2").withFoodType("p").build(),
        new FoodBuilder().withFoodName("Potato chips2").withFoodType("s").build(),
        new FoodBuilder().withFoodName("Chicken Rice2").withFoodType("m").build(),
        new FoodBuilder().withFoodName("Broccoli3").build(),
        new FoodBuilder().withFoodName("Potato3").withFoodType("sv").build(),
        new FoodBuilder().withFoodName("Banana3").withFoodType("f").build(),
        new FoodBuilder().withFoodName("Chicken3").withFoodType("p").build(),
        new FoodBuilder().withFoodName("Potato chips3").withFoodType("s").build(),
        new FoodBuilder().withFoodName("Chicken Rice3").withFoodType("m").build()
        };
    }

    public static Record[] getSampleRecords() {
        return new Record[]{
            new BmiBuilder().build(),
            new BmiBuilder().withDateTime("2019-01-02 09:09").withHeight("1.1").withWeight("45.6").build(),
            new BloodSugarBuilder().build(),
            new BloodSugarBuilder().withDateTime("2019-01-03 09:09").withConcentration("1.23").build(),
        };
    }

    public static CalendarEntry[] getSampleCalendarEntry() {
        Event appointmentEvent = new Event(new Description("Appointment"),
                new DateTime(LocalDateTime.of(2019, 11, 30, 14, 0)));
        appointmentEvent.setEndingDateTime(new DateTime(LocalDateTime.of(2019, 11, 30, 15, 40)));

        Event dinnerEvent = new Event(new Description("Dinner"), new DateTime(LocalDateTime.of(2020, 1, 1, 18, 0)));

        return new CalendarEntry[]{
            new Reminder(new Description("Insulin injection"), new DateTime(LocalDateTime.of(2020, 2, 1, 11, 30)),
                    Repetition.Daily),
            new Reminder(new Description("Medicine"), new DateTime(LocalDateTime.of(2020, 1, 2, 19, 10)),
                    Repetition.Daily),
            new Reminder(new Description("Buy medicine"), new DateTime(LocalDateTime.of(2019, 12, 2, 15, 0)),
                    Repetition.Once),
            appointmentEvent,
            dinnerEvent
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static UniqueFoodList getSampleFoodList() {
        UniqueFoodList sampleFl = new UniqueFoodList();
        for (Food sampleFood : getSampleFoods()) {
            sampleFl.add(sampleFood);
        }
        return sampleFl;
    }

    public static UniqueRecordList getSampleRecordList() {
        UniqueRecordList sampleRl = new UniqueRecordList();
        for (Record sampleRecord : getSampleRecords()) {
            sampleRl.add(sampleRecord);
        }
        return sampleRl;
    }

    public static Calendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        for (CalendarEntry calendarEntry: getSampleCalendarEntry()) {
            sampleCalendar.addCalendarEntry(calendarEntry);
        }
        return sampleCalendar;
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
