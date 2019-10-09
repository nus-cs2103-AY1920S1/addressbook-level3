package seedu.address.logic.commands.itinerary.days.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.day.Day;

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
//        requireNonNull(model);
//        EditDayFieldCommand.EditDayDescriptor editDayDescriptor = model.getPageStatus().getEditDayDescriptor();
//        Day dayToEdit = model.getPageStatus().getDay();
//        Day dayToAdd;
//
//        if (editDayDescriptor == null) {
//            return new CommandResult(MESSAGE_NOT_EDITED);
//        }
//
//        try {
//            if (dayToEdit == null) {
//                //buildTrip() requires all fields to be non null, failing which
//                //NullPointerException is caught below
//                tripToAdd = editDayDescriptor.buildTrip();
//                model.addTrip(tripToAdd);
//            } else {
//                //edit the current "selected" trip
//                tripToAdd = editDayDescriptor.buildTrip(dayToEdit);
//                model.setTrip(dayToEdit, tripToAdd);
//            }
//
//            model.setPageStatus(model.getPageStatus()
//                    .withNewEditTripDescriptor(null)
//                    .withNewPageType(PageType.TRIP_MANAGER)
//                    .withNewTrip(null));
//
//            return new CommandResult(String.format(MESSAGE_EDIT_TRIP_SUCCESS, tripToAdd), true);
//        } catch (NullPointerException | TripNotFoundException ex) {
//            throw new CommandException(MESSAGE_NOT_EDITED);
//        } catch (ClashingTripException ex) {
//            throw new CommandException(MESSAGE_CLASHING_TRIP);
//        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditDayCommand;
    }


}
