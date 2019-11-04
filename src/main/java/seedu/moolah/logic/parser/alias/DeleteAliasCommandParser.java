package seedu.moolah.logic.parser.alias;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;
import java.util.List;

import seedu.moolah.logic.commands.alias.DeleteAliasCommand;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddExpenseCommand object
 */
public class DeleteAliasCommandParser implements Parser<DeleteAliasCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of());

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of());

    /**
     * Parses the given {@code String} of arguments in the context of the AddExpenseCommand
     * and returns an AddExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAliasCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.trim().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAliasCommand.MESSAGE_USAGE));
        }

        return new DeleteAliasCommand(args.trim());
    }

}
