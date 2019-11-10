package seedu.address.logic.commands.itinerary.events.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.DuplicatedEventNameException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * Placeholder.
 */
public class DoneEditEventCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited event information.";

    public static final String MESSAGE_CREATE_EVENT_SUCCESS = "Created Event: %1$s";
    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_NOT_FOUND = "Event is not found!";
    public static final String MESSAGE_EXPENSE_NOT_FOUND = "Expense is not found!";
    public static final String MESSAGE_CLASHING_EVENT = "This event clashes with one of your other events!";
    public static final String MESSAGE_EXPENSE_DUPLICATED_NAME = "This event has the same name with one of your "
            + "other events on the same day!";

    public DoneEditEventCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor = model.getPageStatus().getEditEventDescriptor();
        Event eventToEdit = model.getPageStatus().getEvent();
        Event eventToAdd;

        if (editEventDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            CommandResult commandResult;
            if (eventToEdit == null) {
                //buildEvent() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                eventToAdd = editEventDescriptor.buildEvent(model);
                model.getPageStatus().getDay().getEventList().add(eventToAdd);
                if (eventToAdd.getExpense().isPresent()) {
                    model.getPageStatus().getTrip().getExpenseList().add(eventToAdd.getExpense().get());
                }
                commandResult = new CommandResult(String.format(MESSAGE_CREATE_EVENT_SUCCESS, eventToAdd), true);
            } else {
                //edit the current "selected" event
                eventToAdd = editEventDescriptor.buildEvent(eventToEdit, model);
                model.getPageStatus().getDay().getEventList().set(eventToEdit, eventToAdd);
                if (eventToEdit.getExpense().isPresent()) {
                    model.getPageStatus().getTrip().getExpenseList().remove(eventToEdit.getExpense().get());
                }
                if (eventToAdd.getExpense().isPresent()) {
                    model.getPageStatus().getTrip().getExpenseList().add(eventToAdd.getExpense().get());
                }
                commandResult = new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToAdd), true);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditEventDescriptor()
                    .withNewPageType(PageType.EVENT_PAGE)
                    .withResetEvent());

            return commandResult;
        } catch (NullPointerException | EventNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        } catch (ClashingEventException ex) {
            throw new CommandException(MESSAGE_CLASHING_EVENT);
        } catch (ExpenseNotFoundException e) {
            throw new CommandException(MESSAGE_EXPENSE_NOT_FOUND);
        } catch (DuplicatedEventNameException e) {
            throw new CommandException(MESSAGE_EXPENSE_DUPLICATED_NAME);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditEventCommand;
    }


}
