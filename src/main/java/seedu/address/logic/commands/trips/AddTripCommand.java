package seedu.address.logic.commands.trips;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pagestatus.PageType;
import seedu.address.ui.Ui;

public class AddTripCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the add trip menu of TravelPal. ";

    public static final String MESSAGE_SUCCESS = "Entered add trip screen.";

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        model.setPageStatus(
                model.getPageStatus().withNewPageType(PageType.ADD_TRIP));

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof AddTripCommand;
    }
}
