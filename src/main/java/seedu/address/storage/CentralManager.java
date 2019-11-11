package seedu.address.storage;

import java.util.Objects;

import seedu.address.model.CustomerManager;
import seedu.address.model.DriverManager;
import seedu.address.model.company.Company;
import seedu.address.model.id.IdManager;
import seedu.address.model.task.TaskManager;

/**
 * Wraps all the data needed to be used for saving and loading.
 */
public class CentralManager {

    private CustomerManager customerManager;
    private DriverManager driverManager;
    private TaskManager taskManager;
    private IdManager idManager;
    private Company company;

    public CentralManager() {
        this.customerManager = new CustomerManager();
        this.driverManager = new DriverManager();
        this.taskManager = new TaskManager();
        this.idManager = new IdManager();
        this.company = new Company();
    }

    public CentralManager(CustomerManager customerManager, DriverManager driverManager,
                          TaskManager taskManager, IdManager idManager, Company company) {
        this.customerManager = customerManager;
        this.driverManager = driverManager;
        this.taskManager = taskManager;
        this.idManager = idManager;
        this.company = company;
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

    public Company getCompany() {
        return company;
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
                && getIdManager().equals(that.getIdManager())
                && getCompany().equals(that.getCompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerManager(), getDriverManager(), getTaskManager());
    }
}
