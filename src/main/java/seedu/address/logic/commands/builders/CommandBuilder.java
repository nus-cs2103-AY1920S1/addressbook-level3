package seedu.address.logic.commands.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.arguments.CommandArgument;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.commands.options.CommandOption;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an object which can build commands.
 * Responsible for giving the correct user input to the correct arguments.
 */
public abstract class CommandBuilder {

    // Context is the current CommandOption to add arguments to. Default CommandOption is null.
    private CommandOption context;

    private Integer argumentIndex;
    private Map<CommandOption, Integer> optionsIndex;

    private List<CommandArgument> arguments;
    private Map<String, CommandOption> options;

    CommandBuilder() {
        this.argumentIndex = 0;
        this.optionsIndex = new HashMap<>();
    }

    /**
     * Accepts a sentence from user input tokens.
     * A sentence can either be an option or an argument.
     * @param sentence a sentence from user input
     * @throws ParseException thrown if the sentence is an argument, but failed to be parsed
     */
    public void acceptSentence(String sentence) throws ParseException {
        this.arguments = new ArrayList<>(getCommandArguments());
        this.options = new HashMap<>(getCommandOptions());

        if (this.options.containsKey(sentence)) {
            this.setContext(sentence);
        } else {
            this.addArgument(sentence);
        }
    }

    /**
     * Adds an argument to the appropriate context.
     * @param argument the argument
     * @throws ParseException if the argument failed to be parsed
     */
    private void addArgument(String argument) throws ParseException {
        if (this.context == null) {
            if (this.argumentIndex < this.arguments.size()) {
                this.arguments.get(this.argumentIndex).setValue(argument);
                this.argumentIndex++;
            }
        } else {
            Integer optionIndex = this.optionsIndex.getOrDefault(this.context, 0);
            if (optionIndex < this.context.getArguments().size()) {
                this.context.getArguments().get(optionIndex).setValue(argument);
                this.optionsIndex.put(this.context, optionIndex + 1);
            }
        }
    }

    private void setContext(String option) {
        this.context = this.options.get(option);
    }

    public abstract List<CommandArgument> getCommandArguments();

    public abstract Map<String, CommandOption> getCommandOptions();

    public abstract Command build() throws ArgumentException;
}
