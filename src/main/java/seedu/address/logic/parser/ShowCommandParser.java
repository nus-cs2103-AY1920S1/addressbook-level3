package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;

/**
 * A class to parse show commands from user.
 */
public class ShowCommandParser implements Parser<ShowCommand> {
    @Override
    public ShowCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUPNAME);
        if (!hasOnlyOneParam(argMultimap) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new ShowCommand<Name>(name);
        } else {
            //Group name.
            GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUPNAME).get());
            return new ShowCommand<GroupName>(groupName);
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
