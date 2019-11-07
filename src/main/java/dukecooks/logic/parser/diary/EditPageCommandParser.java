package dukecooks.logic.parser.diary;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_IMAGE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_DESCRIPTION;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_TITLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.diary.EditPageCommand;
import dukecooks.logic.commands.diary.EditPageCommand.EditPageDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.diary.components.DiaryName;


/**
 * Parses input arguments and creates a new EditPageCommand object
 */
public class EditPageCommandParser implements Parser<EditPageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPageCommand
     * and returns an EditPageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPageCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_NAME, PREFIX_PAGE_TITLE, PREFIX_PAGE_TYPE,
                        PREFIX_PAGE_DESCRIPTION, PREFIX_IMAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DIARY_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPageCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPageCommand.MESSAGE_USAGE), pe);
        }

        EditPageDescriptor editPageDescriptor = new EditPageDescriptor();

        DiaryName diaryName = ParserUtil.parseDiaryName(argMultimap.getValue(PREFIX_DIARY_NAME).get());

        if (argMultimap.getValue(PREFIX_PAGE_TITLE).isPresent()) {
            editPageDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_PAGE_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_PAGE_TYPE).isPresent()) {
            editPageDescriptor.setPageType(ParserUtil.parsePageType(argMultimap.getValue(PREFIX_PAGE_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_PAGE_DESCRIPTION).isPresent()) {
            editPageDescriptor.setPageDescription(ParserUtil
                    .parsePageDescription(argMultimap.getValue(PREFIX_PAGE_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_IMAGE).isPresent()) {
            editPageDescriptor.setImage(ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGE).get()));
        }

        if (!editPageDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPageCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPageCommand(index, diaryName, editPageDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
