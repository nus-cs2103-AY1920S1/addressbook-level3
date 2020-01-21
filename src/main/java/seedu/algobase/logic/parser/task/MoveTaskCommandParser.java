package seedu.algobase.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_FROM;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_TO;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.task.MoveTaskCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MoveTaskCommand object
 */
public class MoveTaskCommandParser implements Parser<MoveTaskCommand> {

    private static final Logger logger = LogsCenter.getLogger(MoveTaskCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the MoveTaskCommand
     * and returns a MoveTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing MoveTaskCommand with input: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_PLAN_FROM, PREFIX_PLAN_TO, PREFIX_TASK);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PLAN_FROM)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PLAN_TO)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TASK)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoveTaskCommand.MESSAGE_USAGE));
        }

        Index planFromIndex;
        try {
            planFromIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN_FROM).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoveTaskCommand.MESSAGE_USAGE), pe);
        }

        Index planToIndex;
        try {
            planToIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN_TO).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoveTaskCommand.MESSAGE_USAGE), pe);
        }

        Index taskIndex;
        try {
            taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoveTaskCommand.MESSAGE_USAGE), pe);
        }

        MoveTaskCommand.MoveTaskDescriptor moveTaskDescriptor =
            new MoveTaskCommand.MoveTaskDescriptor(taskIndex, planFromIndex, planToIndex);

        return new MoveTaskCommand(moveTaskDescriptor);
    }

}
