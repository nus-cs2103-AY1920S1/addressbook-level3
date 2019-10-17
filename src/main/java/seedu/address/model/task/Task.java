package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.EventTime;
import seedu.address.model.Goods;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.execeptions.TaskException;

/**
 * Represents a delivery task. All the tasks are represented by a unique id
 * for differentiation.
 */
public class Task {

    public static final String DATE_FORMAT = "d/M/yyyy";

    public static final DateTimeFormatter DATE_FORMAT_FOR_PRINT = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static final DateTimeFormatter DATE_FORMATTER_FOR_USER_INPUT = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private int id;
    private Goods goods;
    private LocalDate date;
    private Optional<Driver> driver;
    private Customer customer;
    private Optional<EventTime> eventTime;

    private TaskStatus status;

    public Task(int id, Goods goods, LocalDate date) {
        this.id = id;
        this.goods = goods;
        this.date = date;
        status = TaskStatus.INCOMPLETE;
        driver = Optional.empty();
        eventTime = Optional.empty();
    }

    //get methods
    public int getId() {
        return id;
    }

    public Goods getGoods() {
        return goods;
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

    public Driver getDriver() {
        if (!driver.isPresent()) {
            throw new TaskException("There is no driver assigned to the task.");
        }
        return driver.get();
    }

    public Customer getCustomer() {
        return customer;
    }

    public EventTime getEventTime() {
        if (!eventTime.isPresent()) {
            throw new TaskException("There is no duration assigned to the task.");
        }

        return eventTime.get();
    }

    public static LocalDate getDateFromString(String date) {
        return LocalDate.parse(date, DATE_FORMATTER_FOR_USER_INPUT);
    }

    public boolean isAssigned() {
        return driver.isPresent();
    }

    //set methods
    public void setStatus(TaskStatus status) {
        if (this.status == status) {
            throw new TaskException("Task's status is already set to " + status);
        }
        this.status = status;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDriver(Driver driver) {
        this.driver = Optional.of(driver);

        setStatus(TaskStatus.ON_GOING);
    }

    public void setEventTime(EventTime eventTime) {
        this.eventTime = Optional.of(eventTime);
    }

    /**
     * Deletes the driver assigned from the task.
     */
    public void deleteDriver() {
        driver = Optional.empty();

        setStatus(TaskStatus.INCOMPLETE);
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

        Task task = (Task) o;
        // If task id is the same, then the task is the same regardless of other variables.
        return getId() == task.getId();
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
                .append(getGoods())
                .append(" Date: ")
                .append(getDatePrint())
                .append(" Delivery Person: ")
                .append(isAssigned() ? getDriver() : "UNASSIGNED")
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }
}
