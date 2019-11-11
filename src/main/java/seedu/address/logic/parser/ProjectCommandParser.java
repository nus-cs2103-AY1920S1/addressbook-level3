package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.util.Date;

/**
 * Parses input arguments and creates a new ProjectCommand object
 */
public class ProjectCommandParser implements Parser<ProjectCommand> {

    @Override
    public ProjectCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_DATE, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElseThrow(AssertionError::new));

        if (date.compareTo(Date.now()) <= 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_USAGE, ProjectCommand.MESSAGE_INVALID_DATE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_CATEGORY)) {
            assert(argMultimap.getValue(PREFIX_CATEGORY).isPresent());
            Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            return new ProjectCommand(date, category);
        }
        return new ProjectCommand(date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
