package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddMemberCommand object
 */
public class AddMemberCommandParser implements Parser<AddMemberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMemberCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_NAME, PREFIX_MEMBER_ID,
                        PREFIX_MEMBER_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_NAME, PREFIX_MEMBER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
        }

        MemberName name = ParserUtil.parseMemberName(argMultimap.getValue(PREFIX_MEMBER_NAME).get());
        MemberId id = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_MEMBER_TAG));

        Member member = new Member(name, id, tagList);

        return new AddMemberCommand(member);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
