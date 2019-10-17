package seedu.address.storage;

import static seedu.address.model.task.Task.DATE_FORMAT;
import static seedu.address.model.task.Task.DATE_FORMATTER_FOR_USER_INPUT;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.Goods;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;
import seedu.address.model.task.execeptions.TaskException;

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
    private String start;
    private String end;

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task task) {
        id = String.valueOf(task.getId());
        description = task.getGoods().getDescription();
        customerId = String.valueOf(task.getCustomer().getId());
        date = task.getDate().format(DATE_FORMATTER_FOR_USER_INPUT);

        try {
            driverId = String.valueOf(task.getDriver().getId());
        } catch (TaskException e) {
            //if no driver assigned
            driverId = null;
        }

        try {
            start = EventTime.getStringFromTime(task.getEventTime().getStart());
            end = EventTime.getStringFromTime(task.getEventTime().getEnd());
        } catch (TaskException e) {
            //if no duration allocated
            start = null;
            end = null;
        }
    }

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("taskId") String id, @JsonProperty("description") String description,
                             @JsonProperty("customerId") String customerId, @JsonProperty("date") String date,
                             @JsonProperty("driverId") String driverId, @JsonProperty("start") String start,
                             @JsonProperty("end") String end) {
        this.id = id;
        this.description = description;
        this.customerId = customerId;
        this.date = date;
        this.driverId = driverId;
        this.start = start;
        this.end = end;
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
        Description goodsDescription = new Description(description);
        final Goods modelGoods = new Goods(goodsDescription);

        if (customerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                            Customer.class.getSimpleName() + " ID"));
        }
        if (!Task.isValidId(customerId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, Customer.class.getSimpleName() + " ID"));
        }
        //final Customer modelCustomer = new Customer();

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                            LocalDate.class.getSimpleName()));
        }
        if (!ParserUtil.isValidDate(date)) {
            throw new IllegalValueException(INVALID_DATE_FORMAT);
        }
        LocalDate modelDate = Task.getDateFromString(date);

        //driverId can be null
        if (driverId != null && !Task.isValidId(driverId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, Driver.class.getSimpleName() + " ID"));
        }
        //final Driver modelDriver = new Driver();

        //Duration's start and end can be null
        if ((start != null || end != null) && !EventTime.isValidDuration(start, end)) {
            throw new IllegalValueException(EventTime.MESSAGE_CONSTRAINTS);
        }

        Task task = new Task(modelTaskId, modelGoods, modelDate);
        if (driverId != null) {
            //get driver from DriverManager
            //assign driver to the task
        }
        if (start != null && end != null) {
            final EventTime modelDuration = EventTime.parse(start, end);
            task.setEventTime(modelDuration);
        }

        return task;
    }


}
