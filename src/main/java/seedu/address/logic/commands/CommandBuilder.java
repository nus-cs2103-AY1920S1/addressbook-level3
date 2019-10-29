package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an object which can build commands.
 * Responsible for giving the correct user input to the correct {@link ArgumentList}.
 */
public abstract class CommandBuilder {

    // Context is the current ArgumentList to add arguments to.
    private ArgumentList context;

    // Represents the command arguments in a Command.
    private ArgumentList commandArguments;

    // Represents the command options in a Command.
    // Key: option keyword, Value: option arguments
    private Map<String, ArgumentList> commandOptions;

    /**
     * This method must be called first.
     */
    CommandBuilder init() {
        RequiredArgumentList arguments = this.defineCommandArguments();
        Map<String, OptionalArgumentList> options = this.defineCommandOptions();

        // Set null values to blank values.
        if (arguments == null) {
            arguments = ArgumentList.required();
        }

        if (options == null) {
            options = Map.of();
        }

        // Build OptionBuilders
        this.commandArguments = arguments.build();
        this.commandOptions = new HashMap<>();
        for (Map.Entry<String, OptionalArgumentList> entry : options.entrySet()) {
            String keyword = entry.getKey();
            OptionalArgumentList option = entry.getValue();
            this.commandOptions.put(keyword, option.build());
        }

        // Set the initial context.
        this.context = this.commandArguments;
        return this;
    }

    /**
     * Accepts a sentence from user input tokens.
     * A sentence can either be an option or an argument.
     *
     * @param sentence a sentence from user input
     */
    public CommandBuilder acceptSentence(String sentence) {
        if (this.commandOptions.containsKey(sentence)) {
            // Sets the context.
            this.context = this.commandOptions.get(sentence);
            this.context.setRequired();
        } else {
            // Adds an argument to the context.
            this.context.acceptArgument(sentence);
        }
        return this;
    }

    /**
     * Builds all arguments and returns a Command.
     *
     * @return the built Command
     * @throws ArgumentException if any argument is required but null
     * @throws ParseException    if any argument is required but null
     */
    public Command build() throws ArgumentException, ParseException {
        this.commandArguments.build();
        for (ArgumentList option : this.commandOptions.values()) {
            option.build();
        }
        return this.commandBuild();
    }

    abstract RequiredArgumentList defineCommandArguments();

    abstract Map<String, OptionalArgumentList> defineCommandOptions();

    abstract Command commandBuild();
}
