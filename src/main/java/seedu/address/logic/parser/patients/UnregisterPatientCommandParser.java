package seedu.address.logic.parser.patients;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.commands.patients.UnregisterPatientCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new DeleteAppCommand object
 */
public class UnregisterPatientCommandParser implements Parser<ReversibleActionPairCommand> {

    private List<Person> lastShownList;

    public UnregisterPatientCommandParser(Model model) {
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnregisterPatientCommand.MESSAGE_USAGE), pe);
        }

        Person personToDelete = ParserUtil.getEntryFromList(lastShownList, index);
        return new ReversibleActionPairCommand(new UnregisterPatientCommand(personToDelete),
            new RegisterPatientCommand(personToDelete));
    }

}
