package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

/**
 * Parses input arguments and creates a new AddMemberToTask parser object
 */
public class AssignCommandParser implements Parser<AssignCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX, PREFIX_MEMBER_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_INDEX, PREFIX_MEMBER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignCommand.MESSAGE_USAGE));
        }

        MemberId id = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());

        return new AssignCommand(index, id);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
