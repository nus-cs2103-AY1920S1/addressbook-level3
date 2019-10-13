package seedu.address.logic.parser;

import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new AddMemberCommand object
 */
public class AddMemberCommandParser implements Parser<AddMemberCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the AddMemberCommand
     * and returns an AddMemberCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddMemberCommand parse (String args) throws ParseException {
        Person person = ParserUtil.parsePersonAdder(args);

        return new AddMemberCommand(person);
    }
}
