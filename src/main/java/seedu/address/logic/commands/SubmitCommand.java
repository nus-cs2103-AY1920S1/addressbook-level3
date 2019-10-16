package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Terminates the program.
 */
public class SubmitCommand extends Command {

    public static final String COMMAND_WORD = "submit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Submitting incident...";

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
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
