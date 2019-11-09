//@@author SakuraBlossom
package seedu.address.logic.parser.staff;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.PersonContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ListStaffCommandParser implements Parser<ListStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListStaffCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListStaffCommand(PREDICATE_SHOW_ALL_PERSONS);
        }

        return new ListStaffCommand(new PersonContainsKeywordPredicate(trimmedArgs));
    }

}
