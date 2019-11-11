package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.GlobalClock;
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

    public static final int SAMPLE_LAST_TASK_ID = 19;
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

        //Event Time selections:
        Optional<EventTime> eventTime9to10 = Optional.of(EventTime.parse("0900 - 1000"));
        Optional<EventTime> eventTime10to12 = Optional.of(EventTime.parse("1000 - 1200"));
        Optional<EventTime> eventTime12to14 = Optional.of(EventTime.parse("1200 - 1400"));
        Optional<EventTime> eventTime14to16 = Optional.of(EventTime.parse("1400 - 1600"));
        Optional<EventTime> eventTime16to18 = Optional.of(EventTime.parse("1600 - 1800"));
        Optional<EventTime> eventTime18to20 = Optional.of(EventTime.parse("1800 - 2000"));
        Optional<EventTime> eventTime20to21 = Optional.of(EventTime.parse("2000 - 2100"));

        //Customer selections:
        Customer customer1 = customerManager.getCustomer(1);
        Customer customer2 = customerManager.getCustomer(2);
        Customer customer3 = customerManager.getCustomer(3);
        Customer customer4 = customerManager.getCustomer(4);
        Customer customer5 = customerManager.getCustomer(5);
        Customer customer6 = customerManager.getCustomer(6);

        //Driver selections:
        Optional<Driver> driver1 = Optional.of(driverManager.getDriver(1));
        Optional<Driver> driver2 = Optional.of(driverManager.getDriver(2));
        Optional<Driver> driver3 = Optional.of(driverManager.getDriver(3));
        Optional<Driver> driver4 = Optional.of(driverManager.getDriver(4));
        Optional<Driver> driver5 = Optional.of(driverManager.getDriver(5));
        Optional<Driver> driver6 = Optional.of(driverManager.getDriver(6));


        //Incomplete delivery tasks:
        Task sampleTask2 = new Task(2, new Description("25 boxes of A4 paper"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask2.setCustomer(customer1);

        Task sampleTask7 = new Task(7, new Description("10 boxes of Coloured paper"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask7.setCustomer(customer1);

        Task sampleTask8 = new Task(8, new Description("1 pair of running shoe"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask8.setCustomer(customer5);

        Task sampleTask14 = new Task(14, new Description("3 Boxes of fresh broccolis"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask14.setCustomer(customer6);

        Task sampleTask15 = new Task(15, new Description("1 bag of rice"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask15.setCustomer(customer5);


        //On-going delivery tasks:
        Task sampleTask3 = new Task(3, new Description("25 packs of frozen chicken wings"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask3.setCustomer(customer1);
        sampleTask3.setDriverAndEventTime(driver1, eventTime9to10);
        driverManager.getDriver(1).addToSchedule(EventTime.parse("0900 - 1000"));

        Task sampleTask9 = new Task(9, new Description("3 dry-fit shirts"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask9.setCustomer(customer5);
        sampleTask9.setDriverAndEventTime(driver4, eventTime16to18);
        driverManager.getDriver(4).addToSchedule(EventTime.parse("1600 - 1800"));

        Task sampleTask10 = new Task(10, new Description("1 Basketball"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask10.setCustomer(customer5);
        sampleTask10.setDriverAndEventTime(driver4, eventTime18to20);
        driverManager.getDriver(4).addToSchedule(EventTime.parse("1800 - 2000"));

        Task sampleTask19 = new Task(19, new Description("4 Boxes of cutlery products"),
                Task.getParsedLocalDate(GlobalClock.dateToday()));
        sampleTask19.setCustomer(customer4);
        sampleTask19.setDriverAndEventTime(driver1, eventTime10to12);
        driverManager.getDriver(1).addToSchedule(EventTime.parse("1000 - 1200"));

        //Completed delivery tasks:
        Task sampleTask1 = new Task(1, new Description("20 frozen boxes of Red groupers"),
                Task.getDateFromString("10/11/2019"));
        sampleTask1.setCustomer(customer1);
        sampleTask1.setDriverAndEventTime(driver1, eventTime9to10);
        sampleTask1.setStatus(TaskStatus.COMPLETED);

        Task sampleTask4 = new Task(4, new Description("5 boxes of Chicken Nuggets"),
                Task.getDateFromString("10/11/2019"));
        sampleTask4.setCustomer(customer2);
        sampleTask4.setDriverAndEventTime(driver2, eventTime14to16);
        sampleTask4.setStatus(TaskStatus.COMPLETED);

        Task sampleTask5 = new Task(5, new Description("1 Lakewood Guitar"),
                Task.getDateFromString("8/11/2019"));
        sampleTask5.setCustomer(customer3);
        sampleTask5.setDriverAndEventTime(driver3, eventTime14to16);
        sampleTask5.setStatus(TaskStatus.COMPLETED);

        Task sampleTask6 = new Task(6, new Description("6 Fidget Spinners"),
                Task.getDateFromString("8/11/2019"));
        sampleTask6.setCustomer(customer3);
        sampleTask6.setDriverAndEventTime(driver2, eventTime16to18);
        sampleTask6.setStatus(TaskStatus.COMPLETED);

        Task sampleTask11 = new Task(11, new Description("3 Boxes of Second-hand books"),
                Task.getDateFromString("9/11/2019"));
        sampleTask11.setCustomer(customer4);
        sampleTask11.setDriverAndEventTime(driver2, eventTime10to12);
        sampleTask11.setStatus(TaskStatus.COMPLETED);

        Task sampleTask12 = new Task(12, new Description("2 Laptop cases"),
                Task.getDateFromString("9/11/2019"));
        sampleTask12.setCustomer(customer4);
        sampleTask12.setDriverAndEventTime(driver4, eventTime14to16);
        sampleTask12.setStatus(TaskStatus.COMPLETED);

        Task sampleTask13 = new Task(13, new Description("5 Phone cases"),
                Task.getDateFromString("9/11/2019"));
        sampleTask13.setCustomer(customer4);
        sampleTask13.setDriverAndEventTime(driver3, eventTime18to20);
        sampleTask13.setStatus(TaskStatus.COMPLETED);

        Task sampleTask16 = new Task(16, new Description("3 Lipsticks"),
                Task.getDateFromString("9/11/2019"));
        sampleTask16.setCustomer(customer2);
        sampleTask16.setDriverAndEventTime(driver4, eventTime10to12);
        sampleTask16.setStatus(TaskStatus.COMPLETED);

        Task sampleTask17 = new Task(17, new Description("1 laptop charger"),
                Task.getDateFromString("9/11/2019"));
        sampleTask17.setCustomer(customer3);
        sampleTask17.setDriverAndEventTime(driver4, eventTime9to10);
        sampleTask17.setStatus(TaskStatus.COMPLETED);

        Task sampleTask18 = new Task(18, new Description("7 long-sleeve cotton shirts"),
                Task.getDateFromString("9/11/2019"));
        sampleTask18.setCustomer(customer4);
        sampleTask18.setDriverAndEventTime(driver4, eventTime12to14);
        sampleTask18.setStatus(TaskStatus.COMPLETED);

        return new Task[]{sampleTask1, sampleTask2, sampleTask3, sampleTask4, sampleTask5,
            sampleTask6, sampleTask7, sampleTask8, sampleTask9, sampleTask10,
            sampleTask11, sampleTask12, sampleTask13, sampleTask14, sampleTask15,
            sampleTask16, sampleTask17, sampleTask18, sampleTask19};
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
