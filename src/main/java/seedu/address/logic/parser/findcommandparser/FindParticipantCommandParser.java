package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.findcommand.FindParticipantCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link FindParticipantCommand} object.
 */
public class FindParticipantCommandParser implements Parser<FindParticipantCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the code {@code FindParticipantCommand}
     * and returns a {@FindParticipantCommand} object for execution.
     * @param args arguments to the command
     * @return {@code FindParticipantCommand}
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindParticipantCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_NAME)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindParticipantCommand.MESSAGE_USAGE));
        }

        String name = argumentMultimap.getValue(PREFIX_NAME).get();
        return new FindParticipantCommand(name);
    }
}
