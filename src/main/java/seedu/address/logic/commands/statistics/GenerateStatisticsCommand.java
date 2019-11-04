package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

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
        List<Event> sortedByStartDateEventList = eventList.stream()
                .sorted(Comparator.comparing(Event::getStartDate))
                .collect(Collectors.toList());
        List<Employee> employeeList = model.getFullListEmployees();

        Map<String, Integer> tagMap = new HashMap<>();
        for (Employee employee : employeeList) {
            Set<Tag> tempSet = employee.getTags();
            for (Tag tag : tempSet) {
                if (tagMap.containsKey(tag.getTagName())) {
                    int count = tagMap.get(tag.getTagName());
                    tagMap.put(tag.getTagName(), count + 1);
                } else {
                    tagMap.put(tag.getTagName(), 1);
                }
            }
        }

        String output = "Quick Summary" + "\n----------------"
                + "\nUpcoming event: " + sortedByStartDateEventList.get(0).getName()
                + "\nTotal number of events: " + eventList.size()
                + "   Total number of employees: " + employeeList.size()
                + "\nNumber of distinct tags: " + tagMap.size();

        for (Map.Entry<String, Integer> entry : tagMap.entrySet()) {
            output += "\n" + entry.getKey() + " : " + entry.getValue();
        }

        return new CommandResult(output, "Statistics");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
