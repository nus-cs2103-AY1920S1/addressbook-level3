package seedu.address.logic.commands.itinerary.days;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.day.Day;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class EnterEditDayCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the day information editing screen\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_EDIT_DAY_SUCCESS = " Welcome to your day! %1$s";

    private final Index indexToEdit;

    public EnterEditDayCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownList = model.getPageStatus().getTrip().getDayList().internalList;

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Day dayToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor = new EditDayFieldCommand.EditDayDescriptor(dayToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_DAY)
                .withNewDay(dayToEdit)
                .withNewEditDayDescriptor(editDayDescriptor));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_DAY_SUCCESS, dayToEdit), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditDayCommand;
    }

}
