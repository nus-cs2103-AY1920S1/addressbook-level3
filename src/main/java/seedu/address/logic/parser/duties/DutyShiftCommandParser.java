//@@author SakuraBlossom
package seedu.address.logic.parser.duties;

import seedu.address.logic.commands.duties.DutyShiftCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.predicates.EventApprovedPredicate;
import seedu.address.model.events.predicates.EventContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class DutyShiftCommandParser implements Parser<DutyShiftCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DutyShiftCommand
     * and returns a DutyShiftCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DutyShiftCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new DutyShiftCommand(new EventApprovedPredicate());
        }
        return new DutyShiftCommand(new EventContainsKeywordPredicate(trimmedArgs));
    }
}
