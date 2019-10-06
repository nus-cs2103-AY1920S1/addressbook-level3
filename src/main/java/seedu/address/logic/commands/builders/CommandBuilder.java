package seedu.address.logic.commands.builders;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.builders.options.Option;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an object which can build commands.
 * Responsible for giving the correct user input to the correct {@link Option}.
 */
public abstract class CommandBuilder {

    // Context is the current Option to add arguments to.
    private Option context;

    // Represents the command arguments in a Command.
    private Option arguments;
    private Map<String, Option> options;

    /**
     * This method must be called first.
     */
    public void initialize() {
        this.arguments = this.getCommandArguments();
        this.options = new HashMap<>(this.getCommandOptions());

        // Set the initial context.
        this.context = this.arguments;
        this.context.setActive();
    }

    /**
     * Accepts a sentence from user input tokens.
     * A sentence can either be an option or an argument.
     * @param sentence a sentence from user input
     * @throws ParseException thrown if the sentence is an argument, but failed to be parsed
     */
    public void acceptSentence(String sentence) throws ParseException {
        if (this.options.containsKey(sentence)) {
            // Sets the context.
            this.context = this.options.get(sentence);
            this.context.setActive();
        } else {
            // Adds an argument to the context.
            this.context.acceptArgument(sentence);
        }
    }

    /**
     * Builds all arguments and returns a Command.
     * @return the built Command
     * @throws ArgumentException if any argument is required but null
     */
    public Command build() throws ArgumentException {
        this.arguments.build();
        for (Option option : this.options.values()) {
            option.build();
        }
        return this.buildCommand();
    }

    abstract Option getCommandArguments();

    abstract Map<String, Option> getCommandOptions();

    abstract Command buildCommand();
}
