package seedu.address.logic.commands;

import java.util.function.Supplier;

import seedu.address.logic.parser.CommandKeywordParser;
import seedu.address.logic.parser.CommandKeywordParserBuilder;

/**
 * Represents a builder that creates {@link CommandManager}.
 */
public class CommandManagerBuilder {

    private final CommandKeywordParserBuilder keywordParserBuilder;
    private CommandKeywordParser keywordParser;

    CommandManagerBuilder() {
        this.keywordParserBuilder = CommandKeywordParser.newBuilder();
    }

    /**
     * Adds a command to be tracked by CommandManager.
     * @param keyword the command keyword
     * @param builder the supplier to invoke
     * @return this builder instance
     */
    public CommandManagerBuilder addCommand(String keyword, Supplier<CommandBuilder> builder) {
        this.keywordParserBuilder.addCommand(keyword, builder);
        return this;
    }

    /**
     * Builds a CommandManager.
     * @return the command manager
     */
    public CommandManager build() {
        this.keywordParser = keywordParserBuilder.build();
        return new CommandManager(this);
    }

    CommandKeywordParser getKeywordParser() {
        return keywordParser;
    }
}
