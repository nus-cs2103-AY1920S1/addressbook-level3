package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.function.Supplier;

import seedu.address.logic.commands.builders.AddEventCommandBuilder;
import seedu.address.logic.commands.builders.CommandBuilder;
import seedu.address.logic.commands.builders.DeleteEventCommandBuilder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser that can derive command keywords, and return the appropriate {@link CommandBuilder}.
 */
public class CommandKeywordParser implements Parser<CommandBuilder> {

    private static final String COMMAND_ADD_EVENT = "add_event";
    private static final String COMMAND_DELETE_EVENT = "delete_event";

    private static final HashMap<String, Supplier<CommandBuilder>> BUILDERS;

    static {
        BUILDERS = new HashMap<>();
        BUILDERS.put(COMMAND_ADD_EVENT, AddEventCommandBuilder::new);
        BUILDERS.put(COMMAND_DELETE_EVENT, DeleteEventCommandBuilder::new);
    }

    @Override
    public CommandBuilder parse(String userInput) throws ParseException {
        if (!BUILDERS.containsKey(userInput)) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        return BUILDERS.get(userInput).get();
    }
}
