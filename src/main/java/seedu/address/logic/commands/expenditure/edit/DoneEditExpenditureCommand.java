package seedu.address.logic.commands.expenditure.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.day.exceptions.DayNotFoundException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * Placeholder.
 */
public class DoneEditExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited expenditure information.";
    public static final String MESSAGE_CREATE_EXPENDITURE_SUCCESS = "Created Expenditure: %1$s";
    public static final String MESSAGE_EDIT_EXPENDITURE_SUCCESS = "Edited Expenditure: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_DUPLICATED_EXPENDITURE = "There is an expenditure with the same name and "
            + "day number, please choose a different name.";
    public static final String MESSAGE_NO_MATCHING_EVENT = "The event associated with the expenditure is not found.";
    public static final String MESSAGE_DAY_INVALID = "The day is not found in your trip! Number of days in your trip: ";
    public static final String MESSAGE_NOT_FOUND = "The expenditure you are editing is not found!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditExpenditureFieldCommand.EditExpenditureDescriptor editExpenditureDescriptor = model.getPageStatus()
                .getEditExpenditureDescriptor();
        Expenditure expenditureToEdit = model.getPageStatus().getExpenditure();
        Expenditure expenditureToAdd;
        int numOfDays = model.getPageStatus().getTrip().getDayList().internalList.size();
        if (editExpenditureDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            CommandResult commandResult;
            if (expenditureToEdit == null) {
                //buildExpenditure() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                expenditureToAdd = editExpenditureDescriptor.buildExpenditure();
                if (expenditureToAdd.getDayNumber().isPresent()) {
                    if (expenditureToAdd.getDayNumber().get().getValue() > numOfDays) {
                        throw new DayNotFoundException();
                    }
                }
                model.getPageStatus().getTrip().getExpenditureList().add(expenditureToAdd);
                commandResult = new CommandResult(String.format(MESSAGE_CREATE_EXPENDITURE_SUCCESS, expenditureToAdd),
                        true);
            } else {
                //edit the current "selected" expenditure
                expenditureToAdd = editExpenditureDescriptor.buildExpenditure(expenditureToEdit);
                if (expenditureToAdd.getDayNumber().isPresent()) {
                    if (expenditureToAdd.getDayNumber().get().getValue() > numOfDays) {
                        throw new DayNotFoundException();
                    }
                }
                model.getPageStatus().getTrip().getExpenditureList().set(expenditureToEdit, expenditureToAdd);
                if ((!expenditureToAdd.getRemovability())) {
                    DayList dayList = model.getPageStatus().getTrip().getDayList();
                    Day day = dayList.internalList.get(expenditureToAdd.getDayNumber().get().getValue() - 1);
                    day.getEventList().updateExpenditure(expenditureToAdd);
                }
                commandResult = new CommandResult(String.format(MESSAGE_EDIT_EXPENDITURE_SUCCESS, expenditureToAdd),
                        true);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditExpenditureDescriptor()
                    .withNewPageType(PageType.EXPENSE_MANAGER)
                    .withResetExpenditure());
            return commandResult;
        } catch (DuplicateExpenditureException ex) {
            throw new CommandException(MESSAGE_DUPLICATED_EXPENDITURE);
        } catch (EventNotFoundException ex) {
            throw new CommandException(MESSAGE_NO_MATCHING_EVENT);
        } catch (DayNotFoundException ex) {
            throw new CommandException(MESSAGE_DAY_INVALID + numOfDays);
        } catch (ExpenditureNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        } catch (NullPointerException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditExpenditureCommand;
    }

}
