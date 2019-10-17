package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;

/**
 * Sorts all the foods in the $aveNUS menu based on given criterion.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String NO_ARGUMENTS_USAGE = "Note you have entered in zero arguments:\n"
            + "Example Usage: " + COMMAND_WORD + " PRICE LESS_THAN 4.00";
    public static final String DUPLICATE_FIELD_USAGE = "Note you have entered a duplicate field.";
    public static final String WRONG_ARGUMENT_NUMBER = "Note that you have key-ed in a wrong number of arguments.\n"
            + "Please fix the formatting to: FIELD QUANTIFIER VALUE\n"
            + "Example Usage: " + COMMAND_WORD + " PRICE LESS_THAN 4.00\n"
            + "Note that VALUE MUST be only one word.";
    public static final String INVALID_FIELD_USAGE = "Note you have entered an invalid field! \n"
             + "You are only allowed to enter the following fields:\n"
             + "NAME, PRICE, CATEGORY, DESCRIPTION, LOCATION, OPENING_HOURS, RESTRICTIONS";
    public static final String INVALID_QUANTIFIER_USAGE = "Note you have entered an invalid quantifier! \n"
             + "You are only allowed to enter the following quantifiers:\n"
             + "LESS_THAN, EQUALS_TO or MORE_THAN";
    public static final String INVALID_VALUE_USAGE = "Note you have entered an invalid value! \n"
             + "Please do make sure your value follow the field requirements.";
    public static final String MESSAGE_SUCCESS = "This is the list of items at your disposal: ";

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
