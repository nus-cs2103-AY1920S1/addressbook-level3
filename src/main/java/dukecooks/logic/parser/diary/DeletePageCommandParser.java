package dukecooks.logic.parser.diary;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.diary.DeletePageCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.diary.components.DiaryName;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            DiaryName diaryName = ParserUtil.parseDiaryName(argMultimap.getValue(PREFIX_DIARY_NAME).get());
            return new DeletePageCommand(index, diaryName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePageCommand.MESSAGE_USAGE), pe);
        }
    }

}
