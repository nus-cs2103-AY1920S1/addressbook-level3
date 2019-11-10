package seedu.algobase.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PROBLEM;

import java.time.LocalDate;
import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.task.AddTaskCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddTaskCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing AddTaskCommand with input: " + args);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_PROBLEM, PREFIX_DUE_DATE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PLAN)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PROBLEM)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Index planIndex;
        try {
            planIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE), pe);
        }

        Index problemIndex;
        try {
            problemIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROBLEM).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE), pe);
        }

        LocalDate targetDate;
        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DUE_DATE)) {
            targetDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DUE_DATE).get());
        } else {
            targetDate = null;
        }

        AddTaskCommand.AddTaskDescriptor addTaskDescriptor =
            new AddTaskCommand.AddTaskDescriptor(planIndex, problemIndex, targetDate);

        return new AddTaskCommand(addTaskDescriptor);
    }

}
