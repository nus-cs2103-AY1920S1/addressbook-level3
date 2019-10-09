package seedu.address.logic.commands.itinerary.days;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

import static java.util.Objects.requireNonNull;

public class EnterCreateDayCommand extends Command {
    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the day creation page of TravelPal.";

    public static final String MESSAGE_SUCCESS = "Entered the day creation screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setPageStatus(
                model.getPageStatus().withNewPageType(PageType.ADD_DAY));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterCreateDayCommand;
    }
}

