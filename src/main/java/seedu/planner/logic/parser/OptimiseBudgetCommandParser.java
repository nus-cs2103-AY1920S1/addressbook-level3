package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

//@@author KxxMxxx
/**
 * Parses arguments for an OptimiseCommand
 */
public class OptimiseBudgetCommandParser implements Parser<OptimiseCommand> {

    /**
     * Parses arguments for an OptimiseCommand and returns an OptimiseCommand.
     * If there are no arguments, a ParseException is thrown.
     */
    public OptimiseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OptimiseCommand.MESSAGE_USAGE));
        }
        Index dayIndex = ParserUtil.parseIndex(trimmedArgs);
        return new OptimiseCommand(dayIndex, false);
    }
}
