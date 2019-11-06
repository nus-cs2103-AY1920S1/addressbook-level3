//@@author SakuraBlossom
package seedu.address.logic.parser.appointments;

import seedu.address.logic.commands.appointments.AppointmentsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.predicates.EventApprovedPredicate;
import seedu.address.model.events.predicates.EventMatchesRefIdPredicate;
import seedu.address.model.person.parameters.PersonReferenceId;

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
        ReferenceId referenceId = PersonReferenceId.lookupPatientReferenceId(trimmedArgs);
        return new AppointmentsCommand(new EventMatchesRefIdPredicate(referenceId));
    }
}
