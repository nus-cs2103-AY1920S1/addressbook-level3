package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.CustomerManager;
import seedu.address.model.Description;
import seedu.address.model.DriverManager;
import seedu.address.model.EventTime;
import seedu.address.model.company.Company;
import seedu.address.model.id.IdManager;
import seedu.address.model.legacy.AddressBook;
import seedu.address.model.legacy.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskManager;
import seedu.address.model.task.TaskStatus;
import seedu.address.storage.CentralManager;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final int SAMPLE_LAST_TASK_ID = 5;
    public static final int SAMPLE_LAST_CUSTOMER_ID = 6;
    public static final int SAMPLE_LAST_DRIVER_ID = 6;

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

    public static Customer[] getSampleCustomer() {
        return new Customer[]{
            new Customer(1, new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new HashSet<>()),
            new Customer(2, new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@hotmail.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new HashSet<>()),
            new Customer(3, new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@gmail.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new HashSet<>()),
            new Customer(4, new Name("David Li"), new Phone("91031282"), new Email("lidavid@hotmail.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new HashSet<>()),
            new Customer(5, new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@gmail.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new HashSet<>()),
            new Customer(6, new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@yahoo.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new HashSet<>())
        };
    }

    public static Driver[] getSampleDriver() {
        return new Driver[]{
            new Driver(1, new Name("Aloysius Chan"), new Phone("92837163"), new Email("aloysius@gmail.com"),
                    new Address("Blk 123 Bukit Panjang Street 10, #11-04"),
                    new HashSet<>()),
            new Driver(2, new Name("Chloe Low"), new Phone("93771823"), new Email("lidavid@hotmail.com"),
                    new Address("Blk 357 Joo Seng Road #07-01 OLIVINE BUILDING, 368357"),
                    new HashSet<>()),
            new Driver(3, new Name("Neo Swee Lian"), new Phone("81678973"), new Email("nsl1980@hotmail.com"),
                    new Address("Blk 34 Boon Lay Street 72, #09-14"),
                    new HashSet<>()),
            new Driver(4, new Name("Russell Lim Wan Bo"), new Phone("82273613"), new Email("wanbo@hotmail.com"),
                    new Address("Blk 305 Lorong 19 Sennett Street 10, #13-01"),
                    new HashSet<>()),
            new Driver(5, new Name("Gerald Guo Weiliang "), new Phone("81739102"), new Email("gerald2000@hotmail.com"),
                    new Address("Blk 384 Boon Lay Street 37, #16-12"),
                    new HashSet<>()),
            new Driver(6, new Name("John Lim"), new Phone("99283312"), new Email("john2222@gmail.com"),
                    new Address("Blk 300 Paya Lebar Street 30, #03-30"),
                    new HashSet<>())
        };
    }

    public static Task[] getSampleTask(CustomerManager customerManager, DriverManager driverManager) {

        Task sampleTask1 = new Task(1, new Description("20 frozen boxes of Red groupers"),
                Task.getDateFromString("10/10/2019"));
        sampleTask1.setCustomer(customerManager.getCustomer(1));
        Optional<Driver> driverOptional = Optional.of(driverManager.getDriver(1));
        Optional<EventTime> eventTimeOptional = Optional.of(EventTime.parse("1000 - 1200"));
        sampleTask1.setDriverAndEventTime(driverOptional, eventTimeOptional);
        sampleTask1.setStatus(TaskStatus.COMPLETED);

        Task sampleTask2 = new Task(2, new Description("25 boxes of A4 paper"),
                Task.getParsedLocalDate(LocalDate.now()));
        sampleTask2.setCustomer(customerManager.getCustomer(1));

        Task sampleTask3 = new Task(3, new Description("25 packs of frozen chicken wings"),
                Task.getParsedLocalDate(LocalDate.now()));
        sampleTask3.setCustomer(customerManager.getCustomer(1));
        Optional<Driver> optionalDriver = Optional.of(driverManager.getDriver(1));
        Optional<EventTime> optionalEventTime = Optional.of(EventTime.parse("1000 - 1200"));
        sampleTask3.setDriverAndEventTime(optionalDriver, optionalEventTime);
        driverManager.getDriver(1).addToSchedule(EventTime.parse("1000 - 1200"));

        Task sampleTask4 = new Task(4, new Description("5 boxes of Chicken Nuggets"),
                Task.getDateFromString("10/10/2019"));
        sampleTask4.setCustomer(customerManager.getCustomer(2));
        Optional<Driver> driverOptional2 = Optional.of(driverManager.getDriver(2));
        Optional<EventTime> eventTimeOptional2 = Optional.of(EventTime.parse("1000 - 1200"));
        sampleTask4.setDriverAndEventTime(driverOptional2, eventTimeOptional2);
        sampleTask4.setStatus(TaskStatus.COMPLETED);

        Task sampleTask5 = new Task(5, new Description("1 Lakewood Guitar"),
                Task.getDateFromString("10/10/2019"));
        sampleTask5.setCustomer(customerManager.getCustomer(3));
        Optional<Driver> driverOptional3 = Optional.of(driverManager.getDriver(1));
        Optional<EventTime> eventTimeOptional3 = Optional.of(EventTime.parse("1400 - 1600"));
        sampleTask5.setDriverAndEventTime(driverOptional3, eventTimeOptional3);
        sampleTask5.setStatus(TaskStatus.COMPLETED);

        return new Task[]{sampleTask1, sampleTask2, sampleTask3, sampleTask4, sampleTask5};
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static CentralManager getSampleCentralManager() {
        CustomerManager sampleCustomerManager = getSampleCustomerManager();
        DriverManager sampleDriverManager = getSampleDriverManager();
        TaskManager sampleTaskManager = getSampleTaskManager(sampleCustomerManager, sampleDriverManager);

        IdManager sampleIdManager = getSampleIdManager();

        Company sampleCompany = getSampleCompany();

        return new CentralManager(sampleCustomerManager, sampleDriverManager, sampleTaskManager,
                sampleIdManager, sampleCompany);
    }

    private static CustomerManager getSampleCustomerManager() {
        CustomerManager sampleCustomerManager = new CustomerManager();
        for (Customer customer : getSampleCustomer()) {
            sampleCustomerManager.addPerson(customer);
        }
        return sampleCustomerManager;
    }

    private static DriverManager getSampleDriverManager() {
        DriverManager sampleDriverManager = new DriverManager();
        for (Driver driver : getSampleDriver()) {
            sampleDriverManager.addPerson(driver);
        }
        return sampleDriverManager;
    }

    private static TaskManager getSampleTaskManager(CustomerManager sampleCustomerManager,
                                                    DriverManager sampleDriverManager) {
        TaskManager sampleTaskManager = new TaskManager();
        for (Task task : getSampleTask(sampleCustomerManager, sampleDriverManager)) {
            sampleTaskManager.addTask(task);
        }
        return sampleTaskManager;
    }

    private static IdManager getSampleIdManager() {
        return new IdManager(SAMPLE_LAST_TASK_ID, SAMPLE_LAST_CUSTOMER_ID, SAMPLE_LAST_DRIVER_ID);
    }

    private static Company getSampleCompany() {
        return new Company();
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
