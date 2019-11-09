package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import seedu.address.logic.GlobalClock;
import seedu.address.model.CustomerManager;
import seedu.address.model.Description;
import seedu.address.model.DriverManager;
import seedu.address.model.EventTime;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.id.IdManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskManager;
import seedu.address.model.task.TaskStatus;
import seedu.address.storage.CentralManager;

/**
 * A utility class containing sample {@link Task} details to be used for testing.
 */
public class SampleEntity {

    public static final LocalDate VALID_DAY_BEFORE_TODAY = GlobalClock.getStaticDate().minusDays(1);
    public static final LocalDate VALID_TODAY_DATE = GlobalClock.getStaticDate();
    public static final LocalDate VALID_DAY_AFTER_TODAY = GlobalClock.getStaticDate().plusDays(1);

    public static final int VALID_TASK_ID = 1;
    public static final Description VALID_DESCRIPTION = new Description("20 frozen boxes of Red groupers");
    public static final LocalDate VALID_LOCAL_DATE = GlobalClock.getStaticDate();
    public static final EventTime VALID_EVENT_TIME = EventTime.parse("1000 - 1230");

    public static final int SECOND_VALID_TASK_ID = 2;
    public static final Description SECOND_VALID_DESCRIPTION = new Description("1 cupboard");
    public static final LocalDate SECOND_VALID_LOCAL_DATE = Task.getDateFromString("13/11/2019");
    public static final EventTime SECOND_VALID_EVENT_TIME = EventTime.parse("1200 - 1430");

    public static final int THIRD_VALID_TASK_ID = 3;
    public static final Description THIRD_VALID_DESCRIPTION = new Description("10 boxes of Blood Oranges");
    public static final LocalDate THIRD_VALID_LOCAL_DATE = GlobalClock.getStaticDate();

    public static final int FOURTH_VALID_TASK_ID = 4;
    public static final Description FOURTH_VALID_DESCRIPTION = new Description("20 Hilti Drills");
    public static final LocalDate FOURTH_VALID_LOCAL_DATE = Task.getDateFromString("14/11/2019");
    public static final EventTime FOURTH_VALID_EVENT_TIME = EventTime.parse("1100 - 1200");

    public static final Customer VALID_CUSTOMER = new Customer(1, new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@gmail.com"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            new HashSet<>());

    public static final Customer SECOND_VALID_CUSTOMER = new Customer(2, new Name("Bernice Yu"),
            new Phone("99272758"), new Email("berniceyu@hotmail.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            new HashSet<>());

    public static final Customer FOURTH_VALID_CUSTOMER = new Customer(4, new Name("David Li"),
            new Phone("91031282"), new Email("lidavid@hotmail.com"),
            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            new HashSet<>());

    /**
     * Please use getFirstSampleDriver() / getSecondSampleDriver() instead.
     * Driver contains mutable elements, so a inappropriately designed test case may
     * contaminate the driver, despite it being static and final.
     */
    public static final Driver VALID_DRIVER = getFirstSampleDriver();
    public static final Driver SECOND_VALID_DRIVER = getSecondSampleDriver();

    /**
     * Builds a fresh Driver.
     *
     * @return a fresh driver.
     */
    public static Driver getFirstSampleDriver() {
        return new Driver(1, new Name("Aloysius Chan"),
                new Phone("92837163"), new Email("aloysius@gmail.com"),
                new Address("Blk 123 Bukit Panjang Street 10, #11-04"),
                new HashSet<>());
    }

    public static Driver getSecondSampleDriver() {
        return new Driver(2, new Name("Chloe Low"),
                new Phone("93771823"), new Email("lidavid@hotmail.com"),
                new Address("Blk 357 Joo Seng Road #07-01 OLIVINE BUILDING, 368357"),
                new HashSet<>());
    }

    public static Driver getFourthSampleDriver() {
        return new Driver(4, new Name("Russell Lim Wan Bo"),
                new Phone("82273613"), new Email("wanbo@hotmail.com"),
                new Address("Blk 305 Lorong 19 Sennett Street 10, #13-01"),
                new HashSet<>());
    }


    public static Task getUnassignedTask(int taskId, Description description, LocalDate date, Customer customer) {
        Task t = new Task(taskId, description, date);
        t.setCustomer(customer);
        return t;
    }

    /**
     * Gets a sample on going task. The date will always be set in a fixed today date so that
     * we can populate the driver today schedule.
     */
    public static Task getOnGoingTask(int taskId, Description description, Customer customer,
                                      Driver driver, EventTime eventTime) {
        Task newTask = new Task(taskId, description, GlobalClock.getStaticDate());
        newTask.setCustomer(customer);
        newTask.setDriver(Optional.of(driver));
        newTask.setEventTime(Optional.of(eventTime));

        //populate driver schedule
        driver.addToSchedule(eventTime);

        return newTask;
    }

    public static Task getCompleteTask(int taskId, Description description, LocalDate date, Customer customer,
                                       Driver driver, EventTime eventTime) {
        Task newTask = new Task(taskId, description, date);
        newTask.setCustomer(customer);
        newTask.setDriver(Optional.of(driver));
        newTask.setEventTime(Optional.of(eventTime));
        newTask.setStatus(TaskStatus.COMPLETED);
        return newTask;
    }

    public static Task getFourthSampleOnGoingTask() {
        return getOnGoingTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION,
                FOURTH_VALID_CUSTOMER, getFourthSampleDriver(), FOURTH_VALID_EVENT_TIME);
    }

    public static Task getFirstSampleCompletedTask() {
        return getCompleteTask(VALID_TASK_ID, VALID_DESCRIPTION, VALID_LOCAL_DATE, VALID_CUSTOMER,
                VALID_DRIVER, VALID_EVENT_TIME);
    }

    public static Task getSecondSampleCompletedTask() {
        return getCompleteTask(SECOND_VALID_TASK_ID, SECOND_VALID_DESCRIPTION, SECOND_VALID_LOCAL_DATE,
                SECOND_VALID_CUSTOMER, SECOND_VALID_DRIVER, SECOND_VALID_EVENT_TIME);
    }

    public static Task getFourthSampleCompletedTask() {
        return getCompleteTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                FOURTH_VALID_CUSTOMER, getFourthSampleDriver(), FOURTH_VALID_EVENT_TIME);
    }

    /**
     * Returns a typical Central Manager for testing. Everything inside is a new object. This is the Deliveria
     * equivalent to the {@code getSamplesAddressbook}
     * <p>
     * It includes: 2 drivers, 2 customer and 2 task. One of the task is scheduled for "today" - the static date
     * defined in GlobalClock, the other is scheduled for some day in the future. Both has no driver or EventTime
     * assigned.
     *
     * @return a typical central manager
     */
    public static CentralManager getSampleCentralManager() {
        CustomerManager customerManager = new CustomerManager();
        customerManager.addPerson(VALID_CUSTOMER);
        customerManager.addPerson(SECOND_VALID_CUSTOMER);

        DriverManager driverManager = new DriverManager();
        driverManager.addPerson(getFirstSampleDriver());
        driverManager.addPerson(getSecondSampleDriver());

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(getUnassignedTask(VALID_TASK_ID, VALID_DESCRIPTION, VALID_LOCAL_DATE, VALID_CUSTOMER));
        taskManager.addTask(getUnassignedTask(SECOND_VALID_TASK_ID, SECOND_VALID_DESCRIPTION, SECOND_VALID_LOCAL_DATE,
                SECOND_VALID_CUSTOMER));
        taskManager.addTask(getUnassignedTask(THIRD_VALID_TASK_ID, THIRD_VALID_DESCRIPTION, THIRD_VALID_LOCAL_DATE,
                SECOND_VALID_CUSTOMER));


        return new CentralManager(customerManager, driverManager, taskManager, new IdManager(
                SECOND_VALID_TASK_ID, SECOND_VALID_CUSTOMER.getId(), SECOND_VALID_DRIVER.getId()
        ));
    }

    public static Model getSampleFreshModel() {
        return new ModelManager(SampleEntity.getSampleCentralManager(), new UserPrefs());
    }
}
