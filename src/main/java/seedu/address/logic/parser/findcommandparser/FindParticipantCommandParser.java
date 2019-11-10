package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

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
        FindCommandUtilEnum type = AlfredParserUtil.getFindType(args);
        // Add space for ArgumentTokenizer to work correctly
        String andOrString = " " + AlfredParserUtil.getAndOrString(args);
        String excludeString = " " + AlfredParserUtil.getExcludeString(args);

        ArgumentMultimap argumentMultimapNorm =
                ArgumentTokenizer.tokenize(andOrString, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE);
        ArgumentMultimap argumentMultimapExclude =
                ArgumentTokenizer.tokenize(excludeString, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE);

        Optional<String> nameNorm = argumentMultimapNorm.getValue(PREFIX_NAME);
        Optional<String> emailNorm = argumentMultimapNorm.getValue(PREFIX_EMAIL);
        Optional<String> phoneNorm = argumentMultimapNorm.getValue(PREFIX_PHONE);

        // Get the negative strings here
        Optional<String> nameExclude = argumentMultimapExclude.getValue(PREFIX_NAME);
        Optional<String> emailExclude = argumentMultimapExclude.getValue(PREFIX_EMAIL);
        Optional<String> phoneExclude = argumentMultimapExclude.getValue(PREFIX_PHONE);

        // If no prefixes given, we will throw an error later
        boolean allPrefixesEmpty = nameNorm.isEmpty() && emailNorm.isEmpty()
                && phoneNorm.isEmpty() && nameExclude.isEmpty()
                && emailExclude.isEmpty() && phoneExclude.isEmpty();

        if (allPrefixesEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindParticipantCommand.MESSAGE_USAGE));
        }

        // Checks the position of the AND/OR
        AlfredParserUtil.isFindTypeAtStart(args.trim());

        return new FindParticipantCommand(type, nameNorm, emailNorm, phoneNorm,
            nameExclude, emailExclude, phoneExclude);
    }
}
