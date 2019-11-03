package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.exceptions.TaskException;

/**
 * Represents a delivery task. All the tasks are represented by a unique id
 * for differentiation.
 */
public class Task {

    public static final String MESSAGE_INVALID_ID = "Invalid task ID.";

    public static final String DATE_FORMAT = "d/M/yyyy";

    public static final DateTimeFormatter DATE_FORMAT_FOR_PRINT = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static final DateTimeFormatter DATE_FORMATTER_FOR_USER_INPUT = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private int id;
    private Description description;
    private Customer customer;
    private LocalDate date;
    private Optional<Driver> driver;
    private Optional<EventTime> eventTime;

    private TaskStatus status;

    public Task(int id, Description description, LocalDate date) {
        this.id = id;
        this.description = description;
        this.date = date;
        status = TaskStatus.INCOMPLETE;
        driver = Optional.empty();
        eventTime = Optional.empty();
    }

    //get methods
    public int getId() {
        return id;
    }

    public Description getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDatePrint() {
        return date.format(DATE_FORMAT_FOR_PRINT);
    }

    public Optional<Driver> getDriver() {
        return driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Optional<EventTime> getEventTime() {
        return eventTime;
    }

    public static LocalDate getDateFromString(String date) {
        return LocalDate.parse(date, DATE_FORMATTER_FOR_USER_INPUT);
    }

    public static LocalDate getParsedLocalDate(LocalDate localDate) {
        return LocalDate.parse(localDate.format(DATE_FORMATTER_FOR_USER_INPUT), DATE_FORMAT_FOR_PRINT);
    }

    public boolean isAssigned() {
        return driver.isPresent();
    }

    public boolean isDurationAssigned() {
        return eventTime.isPresent();
    }

    //set methods
    public void setStatus(TaskStatus status) {
        if (this.status == status) {
            throw new TaskException("Task's status is already set to " + status);
        }
        this.status = status;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDriver(Optional<Driver> driver) {
        if (driver.isPresent()) {
            setStatus(TaskStatus.ON_GOING);
        }

        if (driver.isEmpty() && getStatus().equals(TaskStatus.ON_GOING)) {
            setStatus(TaskStatus.INCOMPLETE);
        }

        this.driver = driver;
    }

    public void setEventTime(Optional<EventTime> eventTime) {
        this.eventTime = eventTime;
    }

    public void setDriverAndEventTime(Optional<Driver> optionalDriver, Optional<EventTime> optionalEventTime) {
        setDriver(optionalDriver);
        setEventTime(optionalEventTime);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void markAsDone() {
        setStatus(TaskStatus.COMPLETED);
    }

    //check methods

    /**
     * Checks if {@code String id} can be parse into an integer and must be more than 0.
     *
     * @param id a unique number in string.
     */
    public static boolean isValidId(String id) {
        try {
            int tempInt = Integer.parseInt(id);
            return (tempInt > 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task otherTask = (Task) o;
        return otherTask.getId() == getId()
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getCustomer().equals(getCustomer())
                && otherTask.getDate().equals(getDate())
                && otherTask.getDriver().equals(getDriver())
                && otherTask.getEventTime().equals(getEventTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Task ID: ")
                .append(getId())
                .append(" Goods: ")
                .append(getDescription())
                .append(" Date: ")
                .append(getDatePrint())
                .append(" Customer ID: ")
                .append(getCustomer().getId())
                .append(" Delivery Person ID: ")
                .append(isAssigned() ? getDriver().get().getId() : "UNASSIGNED")
                .append(" Duration: ")
                .append(isDurationAssigned() ? getEventTime().get() : "NOT ALLOCATED")
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }
}
