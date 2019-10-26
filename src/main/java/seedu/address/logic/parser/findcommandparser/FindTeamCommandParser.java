package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;

import java.util.Optional;

import seedu.address.logic.commands.findcommand.FindTeamCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link FindTeamCommand} object.
 */
public class FindTeamCommandParser implements Parser<FindTeamCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the code {@code FindTeamCommand}
     * and returns a {@FindTeamCommand} object for execution.
     * @param args arguments to the command
     * @return {@code FindTeamCommand}
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PROJECT_NAME);

        Optional<String> name = argumentMultimap.getValue(PREFIX_NAME);
        Optional<String> projectName = argumentMultimap.getValue(PREFIX_PROJECT_NAME);

        boolean allPrefixesEmpty = name.isEmpty() && projectName.isEmpty();

        if (allPrefixesEmpty || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTeamCommand.MESSAGE_USAGE));
        }

        return new FindTeamCommand(name, projectName);
    }
}
