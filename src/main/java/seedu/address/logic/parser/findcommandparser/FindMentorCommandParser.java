package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.logic.commands.findcommand.FindMentorCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link FindMentorCommand} object.
 */
public class FindMentorCommandParser implements Parser<FindMentorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the code {@code FindMentorCommand}
     * and returns a {@FindMentorCommand} object for execution.
     * @param args arguments to the command
     * @return {@code FindMentorCommand}
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindMentorCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ORGANISATION);

        Optional<String> name = argumentMultimap.getValue(PREFIX_NAME);
        Optional<String> email = argumentMultimap.getValue(PREFIX_EMAIL);
        Optional<String> phone = argumentMultimap.getValue(PREFIX_PHONE);
        Optional<String> organization = argumentMultimap.getValue(PREFIX_ORGANISATION);

        boolean allPrefixesEmpty = name.isEmpty() && email.isEmpty()
                && phone.isEmpty() && organization.isEmpty();

        if (allPrefixesEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindMentorCommand.MESSAGE_USAGE));
        }

        return new FindMentorCommand(name, email, phone, organization);
    }
}

