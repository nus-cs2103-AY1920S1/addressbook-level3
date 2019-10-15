package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAGE_NUMBER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.DiaryName;

/**
 * Parses input arguments and creates a new DeletePageCommand object
 */
public class DeletePageCommandParser implements Parser<DeletePageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePageCommand
     * and returns a DeletePageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePageCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_NAME, PREFIX_PAGE_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_DIARY_NAME, PREFIX_PAGE_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePageCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PAGE_NUMBER).get());
            DiaryName diaryName = ParserUtil.parseName(argMultimap.getValue(PREFIX_DIARY_NAME).get());
            return new DeletePageCommand(index, diaryName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePageCommand.MESSAGE_USAGE), pe);
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
