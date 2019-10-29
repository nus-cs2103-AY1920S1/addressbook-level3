package seedu.address.model.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;

/**
 * List of employees allocated as manpower for the Event.
 */
public class EventManpowerAllocatedList {
    private List<EmployeeId> manpowerList;

    public EventManpowerAllocatedList(String unprocessedList) {
        if (unprocessedList.equals("")) {
            manpowerList = new ArrayList<>();
            return;
        }
        String[] personIds = unprocessedList.split(" ");
        manpowerList = Arrays.stream(personIds)
                .map(personId -> new EmployeeId(personId))
                .collect(Collectors.toList());
    }

    public EventManpowerAllocatedList(List<EmployeeId> manpowerList) {
        this.manpowerList = manpowerList;
    }

    /**
     * Constructs a {@code EventManpowerAllocatedList}.
     */
    public EventManpowerAllocatedList() {
        manpowerList = new ArrayList<>();
    }

    public List<EmployeeId> getManpowerList() {
        return manpowerList;
    }

    /**
     * Removes employee to the Manpower List for an Event.
     *
     * @param employee to be removed
     * @return boolean to represent if employee is successfully allocated to event
     */
    public boolean removeEmployee(Employee employee) {
        String employeeId = employee.getEmployeeId().id;
        if (!manpowerList.contains(employeeId)) {
            return false;
        } else {
            return manpowerList.remove(employeeId);
        }
    }

    @Override
    public String toString() {
        StringBuilder unprocessedList = new StringBuilder();
        for (EmployeeId s : manpowerList) {
            unprocessedList.append(s + " ");
        }
        return unprocessedList.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventManpowerAllocatedList // instanceof handles nulls
                && manpowerList.equals(((EventManpowerAllocatedList) other).manpowerList)); // state check
    }

    @Override
    public int hashCode() {
        return manpowerList.hashCode();
    }



}
