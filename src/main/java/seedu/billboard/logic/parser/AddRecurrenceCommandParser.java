package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.logic.commands.AddRecurrenceCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Name;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddRecurrenceCommandParser implements Parser<AddRecurrenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRecurrenceCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRecurrenceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG,
                PREFIX_INTERVAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_END_DATE, PREFIX_INTERVAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecurrenceCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)
                .orElse(" "));

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        CreatedDateTime createdDateTime = argMultimap.getValue(PREFIX_START_DATE).isPresent()
                ? ParserUtil.parseCreatedDateTime(argMultimap.getValue(PREFIX_START_DATE).get())
                : new CreatedDateTime(LocalDateTime.now());

        CreatedDateTime endDateTime = argMultimap.getValue(PREFIX_END_DATE).isPresent()
                ? ParserUtil.parseCreatedDateTime(argMultimap.getValue(PREFIX_END_DATE).get())
                : new CreatedDateTime(LocalDateTime.now());

        List<String> tagList = ParserUtil.parseTagNames(argMultimap.getAllValues(PREFIX_TAG));

        DateInterval interval = ParserUtil.parseInterval(argMultimap.getValue(PREFIX_INTERVAL).get());

        int iterations = ParserUtil.parseIterations(
                DateRange.fromClosed(createdDateTime.dateTime.toLocalDate(), endDateTime.dateTime.toLocalDate()),
                interval);

        return new AddRecurrenceCommand(name, description, amount, createdDateTime, tagList, interval, iterations);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
