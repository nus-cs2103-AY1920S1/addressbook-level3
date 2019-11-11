package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;
import seedu.address.ui.MainWindow;

/**
 * Generates statistics of both events and employees, providing a quick
 * overview of the application data.
 */
public class GenerateStatisticsDetailCommand extends Command {
    public static final String COMMAND_WORD = "generate_stats_detail";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates detailed statistics regarding events and employees."
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Detailed Statistics Generated";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_STATS);
        }
        StringBuilder output = new StringBuilder("");
        List<Employee> employeeList = model.getFullListEmployees();
        FilteredList<Employee> maleEmployeeList = model.getFullListEmployees()
                .filtered(employee -> employee.getEmployeeGender().gender.toLowerCase().equals("male"));
        FilteredList<Employee> femaleEmployeeList = model.getFullListEmployees()
                .filtered(employee -> employee.getEmployeeGender().gender.toLowerCase().equals("female"));
        List<Event> eventList = model.getFullListEvents();
        List<Event> upcomingEventsList = model.getFilteredEventList()
                .stream()
                .filter(event -> !event.isPastEvent())
                .collect(Collectors.toList());
        List<Event> eventsRequireManpowerList = model.getFilteredEventList()
                .stream()
                .filter(event -> !event.isPastEvent())
                .filter(event -> event.getCurrentManpowerCount() < event.getManpowerNeeded().value)
                .collect(Collectors.toList());
        Map<String, Integer> employeeTagMap = new TreeMap<>();
        Map<String, Integer> eventTagMap = new TreeMap<>();
        Set<Tag> tempSet;

        for (Employee employee : employeeList) {
            tempSet = employee.getTags();
            for (Tag tag : tempSet) {
                if (employeeTagMap.containsKey(tag.getTagName())) {
                    int count = employeeTagMap.get(tag.getTagName());
                    employeeTagMap.put(tag.getTagName(), count + 1);
                } else {
                    employeeTagMap.put(tag.getTagName(), 1);
                }
            }
        }

        for (Event event : eventsRequireManpowerList) {
            tempSet = event.getTags();
            for (Tag tag : tempSet) {
                if (eventTagMap.containsKey(tag.getTagName())) {
                    int count = eventTagMap.get(tag.getTagName());
                    eventTagMap.put(tag.getTagName(), count + 1);
                } else {
                    eventTagMap.put(tag.getTagName(), 1);
                }
            }
        }

        output.append("----------------------------------"
                + "\nEvent Statistics"
                + "\n----------------------------------"
                + "\nUpcoming event: " + upcomingEventsList.get(0).getName()
                + "\nTotal number of events: " + eventList.size()
                + "\nTotal number of upcoming events needing manpower: " + eventsRequireManpowerList.size()
                + "\nNumber of distinct upcoming event tags: " + eventTagMap.size()
                + "\n\nDistinct upcoming event tags and number of events with specified tag"
                + "\n----------------------------------------------------------------");

        for (Map.Entry<String, Integer> entry : eventTagMap.entrySet()) {
            output.append("\n" + entry.getKey() + " : " + entry.getValue());
        }

        output.append("\n\n----------------------------------"
                + "\nEmployee Statistics"
                + "\n----------------------------------"
                + "\nTotal number of employees: " + employeeList.size()
                + "\nTotal number of male employees: " + maleEmployeeList.size()
                + "\nTotal number of female employees: " + femaleEmployeeList.size()
                + "\nNumber of distinct employee tags: " + employeeTagMap.size()
                + "\n\nDistinct employee tags and number of employees with specified tag"
                + "\n--------------------------------------------------------------------");

        employeeTagMap.remove("male");
        employeeTagMap.remove("female");

        for (Map.Entry<String, Integer> entry : employeeTagMap.entrySet()) {
            output.append("\n" + entry.getKey() + " : " + entry.getValue());
        }

        return new CommandResult(MESSAGE_SUCCESS, "Detail", output.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
