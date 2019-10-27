package seedu.address.model.id;

import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * Keeps a record of the last unique id used by {@link Task}, {@link Customer} and {@link Driver}.
 */
public class IdManager {

    //all unique ids start with 1
    private int lastTaskId = 0;
    private int lastCustomerId = 0;
    private int lastDriverId = 0;

    public IdManager() {
    }

    public IdManager(int lastTaskId, int lastCustomerId, int lastDriverId) {
        this.lastTaskId = lastTaskId;
        this.lastCustomerId = lastCustomerId;
        this.lastDriverId = lastDriverId;
    }

    // getters for storage
    public int getLastTaskId() {
        return lastTaskId;
    }

    public int getLastCustomerId() {
        return lastCustomerId;
    }

    public int getLastDriverId() {
        return lastDriverId;
    }

    public int getNextTaskId() {
        return lastTaskId + 1;
    }

    public int getNextCustomerId() {
        return lastCustomerId + 1;
    }

    public int getNextDriverId() {
        return lastDriverId + 1;
    }

    public void setLastTaskId(int lastId) {
        lastTaskId = lastId;
    }

    public void setLastCustomerId(int lastId) {
        lastCustomerId = lastId;
    }

    public void setLastDriverId(int lastId) {
        lastDriverId = lastId;
    }

    public void lastTaskIdPlusOne() {
        lastTaskId++;
    }

    public void lastCustomerIdPlusOne() {
        lastCustomerId++;
    }

    public void lastDriverIdPlusOne() {
        lastDriverId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdManager that = (IdManager) o;
        return getLastTaskId() == that.getLastTaskId()
                && getLastCustomerId() == that.getLastCustomerId()
                && getLastDriverId() == that.getLastDriverId();
    }
}
