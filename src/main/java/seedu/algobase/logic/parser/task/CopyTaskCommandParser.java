package seedu.algobase.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_FROM;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_TO;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.task.CopyTaskCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyTaskCommand object
 */
public class CopyTaskCommandParser implements Parser<CopyTaskCommand> {

    private static final Logger logger = LogsCenter.getLogger(CopyTaskCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the CopyTaskCommand
     * and returns a CopyTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing CopyTaskCommand with input: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_PLAN_FROM, PREFIX_PLAN_TO, PREFIX_TASK);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PLAN_FROM)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PLAN_TO)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TASK)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CopyTaskCommand.MESSAGE_USAGE));
        }

        Index planFromIndex;
        try {
            planFromIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN_FROM).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CopyTaskCommand.MESSAGE_USAGE), pe);
        }

        Index planToIndex;
        try {
            planToIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN_TO).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CopyTaskCommand.MESSAGE_USAGE), pe);
        }

        Index taskIndex;
        try {
            taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CopyTaskCommand.MESSAGE_USAGE), pe);
        }

        CopyTaskCommand.CopyTaskDescriptor copyTaskDescriptor =
            new CopyTaskCommand.CopyTaskDescriptor(taskIndex, planFromIndex, planToIndex);

        return new CopyTaskCommand(copyTaskDescriptor);
    }

}
