package seedu.moolah.logic.parser.budget;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;
import java.util.List;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteExpenseFromBudgetCommand object.
 */
public class DeleteExpenseFromBudgetCommandParser implements Parser<DeleteExpenseFromBudgetCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of());
    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of());

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteExpenseFromBudgetCommand
     * and returns a DeleteExpenseFromBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteExpenseFromBudgetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteExpenseFromBudgetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenseFromBudgetCommand.MESSAGE_USAGE), pe);
        }
    }
}
