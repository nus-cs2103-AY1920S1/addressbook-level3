package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * A utility class containing sample {@link Task} details to be used for testing.
 */
public class SampleEntity {

    public static final int VALID_TASK_ID = 1;
    public static final Description VALID_DESCRIPTION = new Description("20 frozen boxes of Red groupers");
    public static final LocalDate VALID_LOCAL_DATE = Task.getDateFromString("10/10/2019");
    public static final EventTime VALID_EVENT_TIME = EventTime.parse("1000 - 1230");

    public static final int SECOND_VALID_TASK_ID = 2;
    public static final Description SECOND_VALID_DESCRIPTION = new Description("1 cupboard");
    public static final LocalDate SECOND_VALID_LOCAL_DATE = Task.getDateFromString("13/11/2019");
    public static final EventTime SECOND_VALID_EVENT_TIME = EventTime.parse("1200 - 1430");

    public static final Customer VALID_CUSTOMER = new Customer(1, new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@gmail.com"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            new HashSet<>());

    public static final Customer SECOND_VALID_CUSTOMER = new Customer(2, new Name("Bernice Yu"),
            new Phone("99272758"), new Email("berniceyu@hotmail.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            new HashSet<>());

    public static final Driver VALID_DRIVER = new Driver(1, new Name("Aloysius Chan"),
            new Phone("92837163"), new Email("aloysius@gmail.com"),
            new Address("Blk 123 Bukit Panjang Street 10, #11-04"),
            new HashSet<>());

    public static final Driver SECOND_VALID_DRIVER = new Driver(2, new Name("Chloe Low"),
            new Phone("93771823"), new Email("lidavid@hotmail.com"),
                    new Address("Blk 357 Joo Seng Road #07-01 OLIVINE BUILDING, 368357"),
                    new HashSet<>());


    public static Task getIncompleteTask(int taskId, Description description, LocalDate date) {
        return new Task(taskId, description, date);
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

    public static Task getFirstSampleCompletedTask() {
        return getCompleteTask(VALID_TASK_ID, VALID_DESCRIPTION, VALID_LOCAL_DATE, VALID_CUSTOMER,
                VALID_DRIVER, VALID_EVENT_TIME);
    }

    public static Task getSecondSampleCompletedTask() {
        return getCompleteTask(SECOND_VALID_TASK_ID, SECOND_VALID_DESCRIPTION, SECOND_VALID_LOCAL_DATE,
                SECOND_VALID_CUSTOMER, SECOND_VALID_DRIVER, SECOND_VALID_EVENT_TIME);
    }
}
