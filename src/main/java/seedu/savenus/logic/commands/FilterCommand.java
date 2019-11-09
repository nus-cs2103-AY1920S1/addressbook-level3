package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;

//@@author seanlowjk
/**
 * Sorts all the foods in the $aveNUS menu based on given criterion.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_SUCCESS = "This is the list of items at your disposal: ";
    public static final String EXAMPLE_USAGE = "Example Usage: " + COMMAND_WORD
            + " PRICE LESS_THAN 5.00";

    private List<String> fields;

    /**
     * Create a simple Filter Command.
     * @param fields the list of fields.
     */
    public FilterCommand(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFields() {
        return this.fields;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.editFilteredFoodList(fields);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && getFields().equals(((FilterCommand) other).getFields()));
    }
}
