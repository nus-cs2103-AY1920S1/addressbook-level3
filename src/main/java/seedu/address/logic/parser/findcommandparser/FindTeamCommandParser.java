package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;

import java.util.Optional;

import seedu.address.logic.commands.findcommand.FindTeamCommand;
import seedu.address.logic.parser.AlfredParserUtil;
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
        System.out.println(args);
        FindCommandUtilEnum type = AlfredParserUtil.getFindType(args);
        // We pad an extra space before the strings else ArgumentTokenizer will not parse properly
        String andOrString = " " + AlfredParserUtil.getAndOrString(args);
        String excludeString = " " + AlfredParserUtil.getExcludeString(args);

        ArgumentMultimap argumentMultimapNorm =
                ArgumentTokenizer.tokenize(andOrString.toLowerCase(), PREFIX_NAME, PREFIX_PROJECT_NAME);
        ArgumentMultimap argumentMultimapExclude = ArgumentTokenizer
                .tokenize(excludeString.toLowerCase(), PREFIX_NAME, PREFIX_PROJECT_NAME);

        Optional<String> nameNorm = argumentMultimapNorm.getValue(PREFIX_NAME);
        Optional<String> projectNameNorm = argumentMultimapNorm.getValue(PREFIX_PROJECT_NAME);

        // Negative Prefixes
        Optional<String> nameExclude = argumentMultimapExclude.getValue(PREFIX_NAME);
        Optional<String> projectNameExclude = argumentMultimapExclude.getValue(PREFIX_PROJECT_NAME);

        boolean allPrefixesEmpty = nameNorm.isEmpty() && projectNameNorm.isEmpty()
                && nameExclude.isEmpty() && projectNameExclude.isEmpty();

        if (allPrefixesEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTeamCommand.MESSAGE_USAGE));
        }

        // Checks the position of the AND/OR
        AlfredParserUtil.isFindTypeAtStart(args.trim().substring("team".length()));

        return new FindTeamCommand(type, nameNorm, projectNameNorm,
                nameExclude, projectNameExclude);
    }
}
