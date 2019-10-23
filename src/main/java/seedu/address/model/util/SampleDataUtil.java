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
import seedu.sgm.model.food.Calorie;
import seedu.sgm.model.food.Fat;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodName;
import seedu.sgm.model.food.FoodType;
import seedu.sgm.model.food.Gi;
import seedu.sgm.model.food.Sugar;
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

    private static Food buildNewFood(String foodName, String calorie, String gi, String sugar, String fat,
                                     FoodType foodType) {
        return new Food(new FoodName(foodName), new Calorie(calorie), new Gi(gi), new Sugar(sugar), new Fat(fat),
            foodType);
    }

    public static Food[] getSampleFoods() {
        return new Food[]{
            buildNewFood("Asparagus", "20", "8", "2", "0", FoodType.NON_STARCHY_VEGETABLE),
            buildNewFood("Broccoli", "45", "6", "2", "1", FoodType.NON_STARCHY_VEGETABLE),
            buildNewFood("Carrot", "30", "47", "5", "0", FoodType.NON_STARCHY_VEGETABLE),
            buildNewFood("Cabbage", "25", "6", "3", "0", FoodType.NON_STARCHY_VEGETABLE),
            buildNewFood("Tomatoes", "25", "6", "3", "0", FoodType.NON_STARCHY_VEGETABLE),
            buildNewFood("Mushroom", "20", "10", "0", "0", FoodType.NON_STARCHY_VEGETABLE),

            buildNewFood("Sweet Corn", "90", "52", "5", "4", FoodType.STARCHY_VEGETABLE),
            buildNewFood("Brown Rice", "216", "68", "0.4", "1.8", FoodType.STARCHY_VEGETABLE),
            buildNewFood("Buckwheat", "343", "34", "0", "3.4", FoodType.STARCHY_VEGETABLE),
            buildNewFood("Millet", "378", "44", "1.6", "4.3", FoodType.STARCHY_VEGETABLE),
            buildNewFood("Butternut Squash", "82", "51", "4", "0.2", FoodType.STARCHY_VEGETABLE),
            buildNewFood("Pumpkin", "30", "64", "3.2", "0.1", FoodType.STARCHY_VEGETABLE),

            buildNewFood("Apple", "90", "36", "12", "0", FoodType.FRUIT),
            buildNewFood("Cherry", "100", "22", "16", "0", FoodType.FRUIT),
            buildNewFood("Orange", "80", "43", "14", "0", FoodType.FRUIT),
            buildNewFood("Grapefruit", "60", "25", "11", "0", FoodType.FRUIT),
            buildNewFood("Peach", "60", "42", "13", "0.5", FoodType.FRUIT),
            buildNewFood("Pear", "100", "38", "16", "0", FoodType.FRUIT),
            buildNewFood("Plum", "70", "39", "16", "0", FoodType.FRUIT),

            buildNewFood("Kidney beans", "127", "24", "0.3", "0.5", FoodType.PROTEIN),
            buildNewFood("Lentils", "230", "30", "1.8", "0.8", FoodType.PROTEIN),
            buildNewFood("Egg", "75", "0", "0", "5", FoodType.PROTEIN),
            buildNewFood("Cheese", "106", "0", "0", "8.9", FoodType.PROTEIN),
            buildNewFood("Chicken", "143", "0", "0", "8.1", FoodType.PROTEIN),
            buildNewFood("Turkey", "149", "0", "0", "8.3", FoodType.PROTEIN),
            buildNewFood("Duck", "132", "0", "0", "5.9", FoodType.PROTEIN),
            buildNewFood("Lean Pork", "263", "0", "0", "21", FoodType.PROTEIN),
            buildNewFood("Lean Beef", "254", "0", "0", "20", FoodType.PROTEIN),
            buildNewFood("Lean Lamb", "282", "0", "0", "23.4", FoodType.PROTEIN),

            buildNewFood("Diet Yogurt", "154", "41", "17", "3.8", FoodType.SNACK),
            buildNewFood("Fat free Yogurt", "137", "41", "19", "0.5", FoodType.SNACK),
            buildNewFood("Oat Crispbread", "130", "49", "2", "9", FoodType.SNACK),
            buildNewFood("Ginger Nut Biscuit", "55", "37", "5", "2", FoodType.SNACK),
            buildNewFood("Fig Roll", "65", "53", "5.9", "1.5", FoodType.SNACK),

            buildNewFood("Spanish Omelet", "260", "40", "3", "10", FoodType.MEAL),
            buildNewFood("Beef Stew", "320", "40", "9", "7", FoodType.MEAL),
            buildNewFood("Two Cheese Pizza", "420", "40", "4", "10", FoodType.MEAL),
            buildNewFood("Rice with Chicken", "400", "40", "5", "7", FoodType.MEAL),
            buildNewFood("Avocado Tacos", "270", "40", "4", "8", FoodType.MEAL)};
    }

    public static Record[] getSampleRecords() {
        return new Record[]{
            new BmiBuilder().build(),
            new BmiBuilder().withDateTime("2019-01-02 09:09").withHeight("1.1").withWeight("45.6").build(),
            new BloodSugarBuilder().build(),
            new BloodSugarBuilder().withDateTime("2019-01-03 09:09").withConcentration("8.6").build(),
            new BloodSugarBuilder().withDateTime("2019-01-03 09:10").withConcentration("8.7").build(),
            new BloodSugarBuilder().withDateTime("2019-01-04 09:11").withConcentration("7.4").build(),
            new BloodSugarBuilder().withDateTime("2019-01-05 09:12").withConcentration("4.9").build(),
            new BloodSugarBuilder().withDateTime("2019-01-06 09:13").withConcentration("7.4").build(),
            new BloodSugarBuilder().withDateTime("2019-01-07 09:14").withConcentration("7.0").build(),
            new BloodSugarBuilder().withDateTime("2019-01-08 09:15").withConcentration("5.0").build(),
            new BloodSugarBuilder().withDateTime("2019-01-09 09:16").withConcentration("4.2").build(),
            new BloodSugarBuilder().withDateTime("2019-01-10 09:17").withConcentration("4.5").build(),
            new BloodSugarBuilder().withDateTime("2019-02-03 09:18").withConcentration("7.0").build(),
            new BloodSugarBuilder().withDateTime("2019-02-04 09:19").withConcentration("6.2").build(),
            new BloodSugarBuilder().withDateTime("2019-03-03 09:20").withConcentration("8.9").build(),
            new BloodSugarBuilder().withDateTime("2019-03-04 09:21").withConcentration("6.8").build(),
            new BloodSugarBuilder().withDateTime("2019-04-03 09:22").withConcentration("6.9").build(),
            new BloodSugarBuilder().withDateTime("2019-04-04 09:23").withConcentration("4.0").build()
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
        for (CalendarEntry calendarEntry : getSampleCalendarEntry()) {
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
