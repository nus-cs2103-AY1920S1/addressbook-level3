package seedu.algobase.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.task.UndoneTaskCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoneTaskCommand object
 */
public class UndoneTaskCommandParser implements Parser<UndoneTaskCommand> {

    private static final Logger logger = LogsCenter.getLogger(UndoneTaskCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DoneTaskCommand
     * and returns a DoneTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoneTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing UndoneTaskCommand with input: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_TASK);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PLAN)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TASK)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneTaskCommand.MESSAGE_USAGE));
        }

        Index planIndex;
        try {
            planIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PLAN).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UndoneTaskCommand.MESSAGE_USAGE), pe);
        }

        Index taskIndex;
        try {
            taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UndoneTaskCommand.MESSAGE_USAGE), pe);
        }

        UndoneTaskCommand.UndoneTaskDescriptor doneTaskDescriptor =
            new UndoneTaskCommand.UndoneTaskDescriptor(planIndex, taskIndex);

        return new UndoneTaskCommand(doneTaskDescriptor);
    }

}
