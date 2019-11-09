//@@author SakuraBlossom
package seedu.address.logic.parser.appointments;

import seedu.address.logic.commands.appointments.AppointmentsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.predicates.EventApprovedPredicate;
import seedu.address.model.events.predicates.EventContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class AppointmentsCommandParser implements Parser<AppointmentsCommand> {
    private Model model;

    public AppointmentsCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new AppointmentsCommand(new EventApprovedPredicate());
        }
        return new AppointmentsCommand(new EventContainsKeywordPredicate(trimmedArgs));
    }
}
