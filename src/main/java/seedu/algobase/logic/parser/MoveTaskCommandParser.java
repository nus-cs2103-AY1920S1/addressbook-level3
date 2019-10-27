package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_FROM;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_TO;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.MoveTaskCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTaskCommand object
 */
public class MoveTaskCommandParser implements Parser<MoveTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MoveTaskCommand
     * and returns a MoveTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_PLAN_FROM, PREFIX_PLAN_TO, PREFIX_TASK);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAN_FROM)
            || !arePrefixesPresent(argMultimap, PREFIX_PLAN_TO)
            || !arePrefixesPresent(argMultimap, PREFIX_TASK)
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
