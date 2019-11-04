package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import seedu.address.logic.commands.note.FindNoteCommand;
import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.model.task.TaskContainsDatePredicate;
import seedu.address.model.task.TaskContainsTimePredicate;
import seedu.address.model.task.TaskHeadingContainsKeyWordPredicate;

/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns an FindTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public FindTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HEADING, PREFIX_DATE, PREFIX_TIME);

        if (argMultimap.getValue(PREFIX_HEADING).isPresent()) {
            String keywords = argMultimap.getValue(PREFIX_HEADING).get().trim();
            if (keywords.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_EMPTY_STRING));
            }
            String[] headingKeywords = keywords.split("\\s+");
            return new FindTaskCommand(new TaskHeadingContainsKeyWordPredicate(Arrays.asList(headingKeywords)));
        }

        try {
            if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
                LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
                return new FindTaskCommand(new TaskContainsDatePredicate(date));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_EMPTY_DATE
                    + "\n" + pe.getMessage()));
        }

        try {
            if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
                LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
                return new FindTaskCommand(new TaskContainsTimePredicate(time));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_EMPTY_TIME
                    + "\n" + pe.getMessage()));
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }
}
