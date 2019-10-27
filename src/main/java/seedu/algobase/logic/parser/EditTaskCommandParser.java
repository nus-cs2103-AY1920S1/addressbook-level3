package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.EditTaskCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_TASK, PREFIX_DUE_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAN)
            || !arePrefixesPresent(argMultimap, PREFIX_TASK)
            || !arePrefixesPresent(argMultimap, PREFIX_DUE_DATE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        Index planIndex;
        try {
            planIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
        }

        Index taskIndex;
        try {
            taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
        }

        LocalDate targetDate;
        if (arePrefixesPresent(argMultimap, PREFIX_DUE_DATE)) {
            targetDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DUE_DATE).get());
        } else {
            targetDate = null;
        }

        EditTaskCommand.EditTaskDescriptor editTaskDescriptor =
            new EditTaskCommand.EditTaskDescriptor(planIndex, taskIndex, targetDate);

        return new EditTaskCommand(editTaskDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
