package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.function.Supplier;

import seedu.address.logic.commands.CommandBuilder;

public class CommandKeywordParserBuilder {

    private final HashMap<String, Supplier<CommandBuilder>> commandKeywordMap;

    CommandKeywordParserBuilder() {
        this.commandKeywordMap = new HashMap<>();
    }

    public CommandKeywordParserBuilder addCommand(String keyword, Supplier<CommandBuilder> command) {
        this.commandKeywordMap.put(keyword, command);
        return this;
    }

    public CommandKeywordParser build() {
        return new CommandKeywordParser(this);
    }

    HashMap<String, Supplier<CommandBuilder>> getCommandKeywordMap() {
        return commandKeywordMap;
    }
}
