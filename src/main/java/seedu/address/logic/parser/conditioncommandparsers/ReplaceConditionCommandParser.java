package seedu.address.logic.parser.conditioncommandparsers;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.conditioncommands.ReplaceConditionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses String into a ReplaceConditionCommand object;
 */
public class ReplaceConditionCommandParser implements Parser<ReplaceConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddReplaceConditionCommand
     * and returns an AddReplaceConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReplaceConditionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        List<Index> indexes = ParserUtil.parseIndexes(args);
        return new ReplaceConditionCommand(indexes.get(0), indexes.get(1));
    }
}
