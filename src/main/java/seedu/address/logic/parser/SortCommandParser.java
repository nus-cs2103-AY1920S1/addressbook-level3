package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEQUENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SortType;

public class SortCommandParser implements Parser<SortCommand> {

    public SortCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_SEQUENCE);

            if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_SEQUENCE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
            SortType type = ParserUtil.parseSortType(argMultimap.getValue(PREFIX_TYPE).get().toLowerCase());
            String seq = argMultimap.getValue(PREFIX_SEQUENCE).get();
            return new SortCommand(type, seq);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }


    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

