package seedu.address.storage;

import static seedu.address.model.task.Task.DATE_FORMAT;
import static seedu.address.model.task.Task.DATE_FORMATTER_FOR_USER_INPUT;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Description;
import seedu.address.model.DriverManager;
import seedu.address.model.EventTime;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerManager;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Schedule;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Jackson-friendly version of {@link Task}.
 */
public class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    public static final String INVALID_INTEGER_ID = "Task's %s field has a invalid integer id.";
    public static final String INVALID_DATE_FORMAT = "Invalid Date format. Date format should be " + DATE_FORMAT;

    //using String data type for fields to accommodate null value and prevent precision loss.
    private String id;
    private String description;
    private String customerId;
    private String date;
    private String driverId;
    private String duration;
    private String status;

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task task) {
        id = String.valueOf(task.getId());
        description = task.getDescription().getValue();
        customerId = String.valueOf(task.getCustomer().getId());
        date = task.getDate().format(DATE_FORMATTER_FOR_USER_INPUT);

        Optional<Driver> driver = task.getDriver();
        if (driver.isEmpty()) {
            driverId = null;
        } else {
            driverId = String.valueOf(task.getDriver().get().getId());
        }

        Optional<EventTime> eventTime = task.getEventTime();
        if (eventTime.isEmpty()) {
            duration = null;
        } else {
            duration = EventTime.getStringFromDuration(task.getEventTime().get());
        }

        status = task.getStatus().toString();
    }

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("taskId") String id, @JsonProperty("description") String description,
                           @JsonProperty("customerId") String customerId, @JsonProperty("date") String date,
                           @JsonProperty("driverId") String driverId, @JsonProperty("duration") String duration,
                           @JsonProperty("status") String status) {
        this.id = id;
        this.description = description;
        this.customerId = customerId;
        this.date = date;
        this.driverId = driverId;
        this.duration = duration;
        this.status = status;
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType(CustomerManager customerManager, DriverManager driverManager) throws IllegalValueException {
        //Task ID ==================================================================================================
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Task.class.getSimpleName() + " ID"));
        }
        if (!Task.isValidId(id)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, Task.class.getSimpleName() + " ID"));
        }
        final int modelTaskId = Integer.parseInt(id);

        //Description ==============================================================================================
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        //Customer ID ==============================================================================================
        if (customerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Customer.class.getSimpleName() + " ID"));
        }
        if (!Task.isValidId(customerId) || !customerManager.hasCustomer(Integer.parseInt(customerId))) {
            throw new IllegalValueException(Customer.MESSAGE_INVALID_ID);
        }
        final Customer modelCustomer = customerManager.getCustomer(Integer.parseInt(customerId));

        //Delivery Date ===========================================================================================
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        if (!ParserUtil.isValidDate(date)) {
            throw new IllegalValueException(INVALID_DATE_FORMAT);
        }
        final LocalDate modelDate = Task.getDateFromString(date);

        //Driver ID ==============================================================================================
        //driverId can be null
        //if driver is not null, check if the id a valid, then check if there exist a driver with driverId.
        if (driverId != null && (!Task.isValidId(driverId) || !driverManager.hasDriver(Integer.parseInt(driverId)))) {
            throw new IllegalValueException(Driver.MESSAGE_INVALID_ID);
        }

        //Duration ==============================================================================================
        //Duration's can be null
        if (duration != null && !EventTime.isValidEventTime(duration)) {
            throw new IllegalValueException(EventTime.MESSAGE_CONSTRAINTS);
        }

        //Create Task ===========================================================================================
        Task task = new Task(modelTaskId, modelDescription, modelDate);
        task.setCustomer(modelCustomer);

        if (driverId != null) {
            Optional<Driver> driverOptional = driverManager.getDriver(Integer.parseInt(driverId));
            if (driverOptional.isEmpty()) {
                throw new IllegalValueException(Driver.MESSAGE_INVALID_ID);
            }

            task.setDriver(driverOptional);
        }
        //if driver is not null, duration must be not null as well.
        if (duration != null) {
            final EventTime modelEventTime = EventTime.parse(duration);
            task.setEventTime(Optional.of(modelEventTime));
        }

        //status cannot be null
        if (status == null) {
            throw new IllegalValueException(TaskStatus.MESSAGE_CONSTRAINTS);
        }

        if (status.equals(TaskStatus.INCOMPLETE.toString())) {
            task.setStatus(TaskStatus.INCOMPLETE);
        } else if (status.equals(TaskStatus.ON_GOING.toString())) {
            task.setStatus(TaskStatus.ON_GOING);

            //if status is ongoing, then load the eventTime to driver schedule
            Driver driver = driverManager.getDriver(Integer.parseInt(driverId)).get();
            Schedule driverSchedule = driver.getSchedule();
            driverSchedule.add(EventTime.parse(duration));
        } else {
            //task is completed
            task.setStatus(TaskStatus.COMPLETED);
        }

        return task;
    }


}
