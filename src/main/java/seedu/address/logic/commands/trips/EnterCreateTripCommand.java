package seedu.address.logic.commands.trips;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pagestatus.PageType;
import seedu.address.ui.trips.EditTripPage;
import seedu.address.ui.Ui;

import static java.util.Objects.requireNonNull;

public class EnterCreateTripCommand extends Command {
    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the trip creation page of TravelPal. ";

    public static final String MESSAGE_SUCCESS = "Entered the trip creation screen.";

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireNonNull(model);

        model.setPageStatus(
                model.getPageStatus().withNewPageType(PageType.ADD_TRIP));

        ui.switchWindow(EditTripPage.class);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterCreateTripCommand;
    }
}
