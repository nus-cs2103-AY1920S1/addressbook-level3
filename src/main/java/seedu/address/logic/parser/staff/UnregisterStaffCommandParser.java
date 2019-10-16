package seedu.address.logic.parser.staff;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.staff.RegisterStaffCommand;
import seedu.address.logic.commands.staff.UnregisterStaffCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteAppCommand object
 */
public class UnregisterStaffCommandParser implements Parser<ReversibleActionPairCommand> {

    private List<Person> lastShownList;

    public UnregisterStaffCommandParser(Model model) {
        this.lastShownList = model.getFilteredPersonList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an ReversibleActionPairCommand object containing an DeleteCommand for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnregisterStaffCommand.MESSAGE_USAGE), pe);
        }

        Person personToDelete = ParserUtil.getEntryFromList(lastShownList, index);
        return new ReversibleActionPairCommand(
            new UnregisterStaffCommand(personToDelete),
            new RegisterStaffCommand(personToDelete));
    }

}
