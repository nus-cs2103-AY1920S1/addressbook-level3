package seedu.address.logic.commands.itinerary.days.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.exceptions.ClashingDayException;
import seedu.address.model.itinerary.day.exceptions.DayNotFoundException;

import static java.util.Objects.requireNonNull;

public class DoneEditDayCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited day information.";

    public static final String MESSAGE_CREATE_DAY_SUCCESS = "Created Day: %1$s";
    public static final String MESSAGE_EDIT_DAY_SUCCESS = "Edited Day: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_CLASHING_DAY = "This day clashes with one of your other days!";

    public DoneEditDayCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor = model.getPageStatus().getEditDayDescriptor();
        Day dayToEdit = model.getPageStatus().getDay();
        Day dayToAdd;

        if (editDayDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            if (dayToEdit == null) {
                //buildDay() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                dayToAdd = editDayDescriptor.buildDay();
                model.getPageStatus().getTrip().getDayList().add(dayToAdd);
            } else {
                //edit the current "selected" day
                dayToAdd = editDayDescriptor.buildDay(dayToEdit);
                model.getPageStatus().getTrip().getDayList().set(dayToEdit, dayToAdd);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditDayDescriptor()
                    .withNewPageType(PageType.ITINERARY)
                    .withResetDay());

            return new CommandResult(String.format(MESSAGE_EDIT_DAY_SUCCESS, dayToAdd), true);
        } catch (NullPointerException | DayNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        } catch (ClashingDayException ex) {
            throw new CommandException(MESSAGE_CLASHING_DAY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditDayCommand;
    }


}
