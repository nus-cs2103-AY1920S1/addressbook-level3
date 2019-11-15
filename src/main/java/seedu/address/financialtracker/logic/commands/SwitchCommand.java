package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches to another expense list with associated country.
 */
public class SwitchCommand extends Command<Model> {

    public static final String COMMAND_WORD = "switch";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": switch to another country\n"
            + "e.g. switch Japan\n"
            + "Please check the drop down menu for list of countries available :)\n"
            + "Note: You can also use the drop down menu directly!";
    public static final String MESSAGE_SUCCESS = "Expense list switched";

    private final String country;

    /**
     * Creates an SwitchCommand to switch between expense lists of different country.
     */
    public SwitchCommand(String country) {
        requireNonNull(country);
        this.country = country;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateDropDownMenu(country);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && country.equals(((SwitchCommand) other).country));
    }
}
