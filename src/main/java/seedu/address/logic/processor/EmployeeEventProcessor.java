package seedu.address.logic.processor;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Utility Methods that requires the processing of both Employees and Events
 */
public class EmployeeEventProcessor {

    /**
     * Finds the List of Events that an Employee is allocated to
     */
    public static List<Event> employeeAllocatedEvents(Employee employee, List<Event> eventList) {
        return eventList.stream()
                .filter(event -> event.employeeIsAllocated(employee))
                .collect(Collectors.toList());
    }

    /**
     * Finds the total hours that an Employee has worked in the past under the Events that he/she is allocated
     *
     * @param employee  Target Employee
     * @param eventList List of Events
     */
    public static double findEmployeeTotalWorkedHours(Employee employee, List<Event> eventList) {
        List<Event> allocatedEvents = employeeAllocatedEvents(employee, eventList);
        return allocatedEvents.stream()
                .filter(event -> event.isPastEvent())
                .mapToDouble(event -> event.getEventTotalHours())
                .sum();
    }

}
