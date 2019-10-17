package seedu.address.logic.commands;

import java.util.Comparator;
import java.util.Iterator;

import javafx.collections.ObservableList;

import seedu.address.model.Model;
import seedu.address.model.incident.Incident;

/**
 * Terminates the program.
 */
public class SubmitCommand extends Command {

    public static final String COMMAND_WORD = "submit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Submits the latest draft.";

    public static final String MESSAGE_SUCCESS = "New incident report submitted: %1$s";

    @Override
    public CommandResult execute(Model model) {
        ObservableList<Incident> incidents = model.getFilteredIncidentList();
        Comparator<Incident> comparator = new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                if (o1.getDateTime().incidentDateTime.isBefore(o2.getDateTime().incidentDateTime)) {
                    return -1;
                } else if (o1.getDateTime().incidentDateTime.isAfter(o2.getDateTime().incidentDateTime)) {
                    return 1;
                }
                return 0;
            }
        };

        incidents.sort(comparator);
        Iterator<Incident> it = incidents.iterator();
        Incident toAdd = it.next();
        model.addIncident(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
