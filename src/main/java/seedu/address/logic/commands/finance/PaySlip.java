package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Lists all employees and events in the AddMin+ to the user.
 */
public class PaySlip extends Command {

    public static final String COMMAND_WORD = "payslip";



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Event> eventlist = model.getEventBook().getEventList();
        int totalH = 0;
        for (Event e : eventlist) {
            totalH = totalH + (int) e.getEventDateTimeMap().totalHours();
        }
        int amount = totalH * 10;
        //output = "Total Payment for all events: $" + amount + "\nBreakdown: " + totalH + " hours, $10.0/h";
        List<Employee> employeeList = model.getAddressBook().getEmployeeList();
        String first = employeeList.get(0).getEmployeeSalaryPaid().value;
        String output = first;
        return new CommandResult(String.format(output),"Finance");
    }
}
