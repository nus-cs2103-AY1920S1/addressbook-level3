package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_IMAGE;

import java.util.stream.Stream;

import seedu.address.logic.commands.SetImageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

public class SetImageCommandParser implements Parser<SetImageCommand> {

    public SetImageCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_ID, PREFIX_MEMBER_IMAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_ID, PREFIX_MEMBER_IMAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetImageCommand.MESSAGE_USAGE));
        }

        MemberId id;
        String url;

        try {
            id = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetImageCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_MEMBER_IMAGE).isPresent()) {
            url = ParserUtil.parseMemberImage(argMultimap.getValue(PREFIX_MEMBER_IMAGE).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetImageCommand.MESSAGE_USAGE));
        }

        return new SetImageCommand(id, url);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
