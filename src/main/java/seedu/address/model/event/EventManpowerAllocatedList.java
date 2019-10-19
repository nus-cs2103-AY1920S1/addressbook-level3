package seedu.address.model.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List of employees allocated as manpower for the Event.
 */
public class EventManpowerAllocatedList {
    private List<String> manpowerList;

    /**
     * Constructs a {@code Name}.
     */
    public EventManpowerAllocatedList() {
        manpowerList = new ArrayList<>();
    }

    public List<String> getManpowerList() {
        return manpowerList;
    }

    public void setManpowerAllocatedList(String unprocessedList) {
        if (unprocessedList.equals("")) {
            return;
        }
        String[] personIds = unprocessedList.split(" ");
        manpowerList = Arrays.stream(personIds).collect(Collectors.toList());
    }

    public int getCurrentManpowerCount() {
        return manpowerList.size();
    }

    /**
     * Allocates employee to the Manpower List for an Event.
     *
     * @param employeeId to be allocated
     * @return boolean to represent if employee is successfully allocated to event
     */
    public boolean allocateEmployee(String employeeId) {
        if (manpowerList.contains(employeeId)) {
            return false;
        } else {
            return manpowerList.add(employeeId);
        }
    }

    @Override
    public String toString() {
        StringBuilder unprocessedList = new StringBuilder();
        for (String s : manpowerList) {
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
