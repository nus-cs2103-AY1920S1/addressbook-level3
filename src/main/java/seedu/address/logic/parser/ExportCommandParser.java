package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;

/**
 * Parser for export commands. Export commands take in a prefix 'n' for name.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUPNAME);
        if (!hasOnlyOneParam(argMultimap) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new ExportCommand<Name>(name);
        } else {
            //Group name.
            GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUPNAME).get());
            return new ExportCommand<GroupName>(groupName);
        }
    }

    /**
     * Method to check and ensure that input has either prefix name or prefix groupname but not both.
     * @param argumentMultimap Argument map to check
     * @return true only when input has only one parameter.
     */
    private boolean hasOnlyOneParam(ArgumentMultimap argumentMultimap) {
        if (arePrefixesPresent(argumentMultimap, PREFIX_NAME)
                && arePrefixesPresent(argumentMultimap, PREFIX_GROUPNAME)) {
            return false;
        } else if (!arePrefixesPresent(argumentMultimap, PREFIX_NAME)
                && !arePrefixesPresent(argumentMultimap, PREFIX_GROUPNAME)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
