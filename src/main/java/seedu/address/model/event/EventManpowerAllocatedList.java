package seedu.address.model.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;

/**
 * List of {@code EmployeeId} allocated as manpower for the Event.
 */
public class EventManpowerAllocatedList {
    private List<EmployeeId> manpowerList;


    /**
     * Constructs an empty {@code EventManpowerAllocatedList} from an unprocessed list
     * which is delimited by empty spaces.
     */
    public EventManpowerAllocatedList(String unprocessedList) {
        if (unprocessedList.equals("")) {
            manpowerList = new ArrayList<>();
            return;
        }

        String[] employeeIdArray = unprocessedList.split(" ");
        manpowerList = Arrays.stream(employeeIdArray)
                .map(EmployeeId::new)
                .collect(Collectors.toList());
    }

    /**
     * Constructs a new {@code EventManpowerAllocatedList} with a given list of {@code EmployeeId}.
     */
    public EventManpowerAllocatedList(List<EmployeeId> manpowerList) {
        this.manpowerList = manpowerList;
    }

    /**
     * Constructs an empty {@code EventManpowerAllocatedList}.
     */
    public EventManpowerAllocatedList() {
        manpowerList = new ArrayList<>();
    }

    public List<EmployeeId> getManpowerList() {
        return manpowerList;
    }


    /**
     * Boolean Method to check if an the allocated list contains an employee
     */
    public boolean containsEmployee(Employee employee) {
        return manpowerList.contains(employee.getEmployeeId());
    }

    @Override
    public String toString() {
        StringBuilder unprocessedList = new StringBuilder();
        for (EmployeeId s : manpowerList) {
            unprocessedList.append(s).append(" ");
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
