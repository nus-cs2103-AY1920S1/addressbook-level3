package seedu.address.model.task;

import java.util.Objects;

import seedu.address.model.task.execeptions.TaskException;

/**
 * Represents a delivery task. All the tasks are represented by a unique id
 * for differentiation.
 */
public class Task {

    private int id;
    private String goods;
    private String dateTime;
    private String driver;
    private String customer;

    private TaskStatus status;

    public Task(int id, String goods) {
        this.id = id;
        this.goods = goods;
        status = TaskStatus.INCOMPLETE;
    }

    //get methods
    public int getId() {
        return id;
    }

    public String getGoods() {
        return goods;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDriver() {
        return driver;
    }

    public String getCustomer() {
        return customer;
    }

    public boolean isAssign() {
        return getDriver() != null;
    }

    //set methods
    public void setStatus(TaskStatus status) {
        if (this.status == status) {
            throw new TaskException("Task's status is already set to " + status);
        }
        this.status = status;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDriver(String driver) {
        this.driver = driver;

        setStatus(TaskStatus.ON_GOING);
    }

    /**
     * Deletes the driver assigned from the task.
     */
    public void deleteDriver() {
        driver = null;

        setStatus(TaskStatus.INCOMPLETE);
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void markAsDone() {
        setStatus(TaskStatus.COMPLETED);
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
        return getId() == task.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public String toString() {
        return getId() + ". " + getGoods() + " " + getStatus();
    }
}
