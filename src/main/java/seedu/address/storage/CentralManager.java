package seedu.address.storage;

import java.util.Objects;

import seedu.address.model.CustomerManager;
import seedu.address.model.DriverManager;
import seedu.address.model.id.IdManager;
import seedu.address.model.task.TaskManager;

/**
 * A wrapper manager that consists of all the managers used for saving purpose.
 */
public class CentralManager {

    private CustomerManager customerManager;
    private DriverManager driverManager;
    private TaskManager taskManager;
    private IdManager idManager;

    public CentralManager() {
        this.customerManager = new CustomerManager();
        this.driverManager = new DriverManager();
        this.taskManager = new TaskManager();
        this.idManager = new IdManager();
    }

    public CentralManager(CustomerManager customerManager, DriverManager driverManager,
                          TaskManager taskManager, IdManager idManager) {
        this.customerManager = customerManager;
        this.driverManager = driverManager;
        this.taskManager = taskManager;
        this.idManager = idManager;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public IdManager getIdManager() {
        return idManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CentralManager that = (CentralManager) o;
        return getCustomerManager().equals(that.getCustomerManager())
                && getDriverManager().equals(that.getDriverManager())
                && getTaskManager().equals(that.getTaskManager())
                && getIdManager().equals(that.getIdManager());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerManager(), getDriverManager(), getTaskManager());
    }
}
