package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.TravelPal;
import seedu.address.model.Model;
import seedu.address.ui.Ui;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, Ui ui) {
        requireNonNull(model);
        model.setTravelPal(new TravelPal());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
