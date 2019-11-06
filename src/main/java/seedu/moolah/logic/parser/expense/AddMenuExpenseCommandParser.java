package seedu.moolah.logic.parser.expense;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.commands.expense.AddMenuExpenseCommand.MESSAGE_USAGE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MENU;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Collections;
import java.util.List;

import seedu.moolah.logic.commands.expense.AddMenuExpenseCommand;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.Timekeeper;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.menu.MenuItem;

/**
 * Parses input arguments and creates a new AddMenuExpenseCommand object
 */
public class AddMenuExpenseCommandParser implements Parser<AddMenuExpenseCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_MENU
    ));

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_TIMESTAMP
    ));

    /**
     * Parses the given {@code String} of arguments in the context of the AddMenuExpenseCommand
     * and returns an AddMenuExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMenuExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MENU, PREFIX_TIMESTAMP);

        if (!argMultimap.arePrefixesPresent(PREFIX_MENU)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        if (argMultimap.hasRepeatedPrefixes(PREFIX_MENU, PREFIX_TIMESTAMP)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        MenuItem menuItem = ParserUtil.parseMenuItem(argMultimap.getValue(PREFIX_MENU).get());
        boolean isTimestampPresent = argMultimap.getValue(PREFIX_TIMESTAMP).isPresent();

        if (isTimestampPresent) {
            Timestamp timestamp = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get());
            if (Timekeeper.isFutureTimestamp(timestamp)) {
                throw new ParseException("Expense cannot be set in the future");
            }
            return new AddMenuExpenseCommand(menuItem, timestamp);
        } else {
            return new AddMenuExpenseCommand(menuItem);
        }
    }

}
