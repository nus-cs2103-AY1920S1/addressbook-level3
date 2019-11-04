package seedu.address.logic.commands.statistics;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.employee.Employee;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Generates statistics of both events and employees, providing a quick
 * overview of the application data.
 */
public class GenerateStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "generate_stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates overall statistics of events and employees."
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Statistics Generated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Event> eventList = model.getFullListEvents();
        /*eventList.sort(Comparator.comparing(Event::getStartDate));*/
        List<Employee> employeeList = model.getFullListEmployees();
        String output = "Quick Summary" + "\n----------------"
                /*+ "Upcoming event: " + eventList.get(0)*/
                + "\nTotal number of events: " + eventList.size()
                + "   Total number of employees: " + employeeList.size();
        return new CommandResult(output, "Statistics");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
