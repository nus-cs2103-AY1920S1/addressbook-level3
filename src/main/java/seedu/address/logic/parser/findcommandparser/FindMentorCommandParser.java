package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.logic.commands.findcommand.FindMentorCommand;
import seedu.address.logic.parser.AlfredParserUtil;
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
        FindCommandUtilEnum type = AlfredParserUtil.getFindType(args);
        String andOrString = " " + AlfredParserUtil.getAndOrString(args);
        String excludeString = " " + AlfredParserUtil.getExcludeString(args);

        ArgumentMultimap argumentMultimapNorm =
                ArgumentTokenizer.tokenize(
                        andOrString, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ORGANISATION);
        ArgumentMultimap argumentMultimapExclude =
                ArgumentTokenizer.tokenize(
                        excludeString, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ORGANISATION);

        Optional<String> nameNorm = argumentMultimapNorm.getValue(PREFIX_NAME);
        Optional<String> emailNorm = argumentMultimapNorm.getValue(PREFIX_EMAIL);
        Optional<String> phoneNorm = argumentMultimapNorm.getValue(PREFIX_PHONE);
        Optional<String> organizationNorm = argumentMultimapNorm.getValue(PREFIX_ORGANISATION);

        // Get negative prefixes
        Optional<String> nameExclude = argumentMultimapExclude.getValue(PREFIX_NAME);
        Optional<String> emailExclude = argumentMultimapExclude.getValue(PREFIX_EMAIL);
        Optional<String> phoneExclude = argumentMultimapExclude.getValue(PREFIX_PHONE);
        Optional<String> organizationExclude = argumentMultimapExclude.getValue(PREFIX_ORGANISATION);

        boolean allPrefixesEmpty = nameNorm.isEmpty() && emailNorm.isEmpty()
                && phoneNorm.isEmpty() && organizationNorm.isEmpty()
                && nameExclude.isEmpty() && emailExclude.isEmpty()
                && phoneExclude.isEmpty() && organizationExclude.isEmpty();

        if (allPrefixesEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindMentorCommand.MESSAGE_USAGE));
        }

        // Checks the position of the AND/OR
        AlfredParserUtil.isFindTypeAtStart(args.trim());

        return new FindMentorCommand(type, nameNorm, emailNorm, phoneNorm, organizationNorm,
                nameExclude, emailExclude, phoneExclude, organizationExclude);
    }
}

