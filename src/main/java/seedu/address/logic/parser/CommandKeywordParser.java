package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser that can derive command keywords, and return the appropriate {@link CommandBuilder}.
 */
public class CommandKeywordParser implements Parser<CommandBuilder> {

    private final HashMap<String, Supplier<CommandBuilder>> commandKeywordMap;

    public CommandKeywordParser() {
        this.commandKeywordMap = new HashMap<>();
    }

    /**
     * Adds a command keyword to this parser. The command provided cannot be null.
     * @param keyword the keyword
     * @param command the command the keyword creates
     */
    public void addCommand(String keyword, Supplier<CommandBuilder> command) {
        Objects.requireNonNull(command);
        Objects.requireNonNull(command.get());
        this.commandKeywordMap.put(keyword, command);
    }

    @Override
    public CommandBuilder parse(String userInput) throws ParseException {
        Objects.requireNonNull(userInput);
        if (!commandKeywordMap.containsKey(userInput)) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        return commandKeywordMap.get(userInput).get();
    }
}
