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
import seedu.address.model.EventTime;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
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
    public Task toModelType() throws IllegalValueException {
        //temp
        // id ok, description ok, customerId NOT OK, date OK,
        //need to check against customerManager list if customer id valid
        //then get the customer and input into task as parameter
        //SAME FOR DRIVER^
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Task.class.getSimpleName() + " ID"));
        }
        if (!Task.isValidId(id)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, Task.class.getSimpleName() + " ID"));
        }
        final int modelTaskId = Integer.parseInt(id);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (customerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Customer.class.getSimpleName() + " ID"));
        }
        if (!Task.isValidId(customerId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, Customer.class.getSimpleName() + " ID"));
        }
        //check if customer exists in the list
        // final Customer modelCustomer = new Customer();

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        if (!ParserUtil.isValidDate(date)) {
            throw new IllegalValueException(INVALID_DATE_FORMAT);
        }
        final LocalDate modelDate = Task.getDateFromString(date);

        //driverId can be null
        if (driverId != null && !Task.isValidId(driverId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, Driver.class.getSimpleName() + " ID"));
        }
        //final Driver modelDriver = new Driver();

        //Duration's can be null
        if (duration != null && !EventTime.isValidEventTime(duration)) {
            throw new IllegalValueException(EventTime.MESSAGE_CONSTRAINTS);
        }

        Task task = new Task(modelTaskId, modelDescription, modelDate);
        if (driverId != null) {
            //get driver from DriverManager
            //assign driver to the task
        }
        if (duration != null) {
            final EventTime modelEventTime = EventTime.parse(duration);
            task.setEventTime(Optional.of(modelEventTime));
        }

        //status cannot be null
        if (status != null) {
            throw new IllegalValueException(TaskStatus.MESSAGE_CONSTRAINTS);
        }
        if (status.equals(TaskStatus.INCOMPLETE.toString())) {
            task.setStatus(TaskStatus.INCOMPLETE);
        } else if (status.equals(TaskStatus.ON_GOING.toString())) {
            task.setStatus(TaskStatus.ON_GOING);
        } else {
            //task is completed
            task.setStatus(TaskStatus.COMPLETED);
        }

        return task;
    }


}
