package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.OptimiseBudgetCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for an OptimiseBudgetCommand
 */
public class OptimiseBudgetCommandParser implements Parser<OptimiseBudgetCommand> {

    /**
     * Parses arguments for an OptimiseBudgetCommand and returns an OptimiseBudgetCommand.
     * If there are no arguments, a ParseException is thrown.
     */
    public OptimiseBudgetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OptimiseBudgetCommand.MESSAGE_USAGE));
        }
        Index dayIndex = ParserUtil.parseIndex(trimmedArgs);
        return new OptimiseBudgetCommand(dayIndex);
    }
}
