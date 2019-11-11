package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success! \n Redo command: %1$s";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";



    private ReadOnlyDataBook<Customer> previousCustomerBook;
    private ReadOnlyDataBook<Order> previousOrderBook;
    private ReadOnlyDataBook<Phone> previousPhoneBook;
    private ReadOnlyDataBook<Schedule> previousScheduleBook;
    private ReadOnlyDataBook<Order> previousArchivedOrderBook;


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        previousCustomerBook = new DataBook<>(model.getCustomerBook());
        previousOrderBook = new DataBook<>(model.getOrderBook());
        previousPhoneBook = new DataBook<>(model.getPhoneBook());
        previousScheduleBook = new DataBook<>(model.getScheduleBook());
        previousArchivedOrderBook = new DataBook<>(model.getArchivedOrderBook());

        UndoableCommand toRedoCommand = undoRedoStack.popRedo();
        toRedoCommand.redo(model);

        Model oldModel = new ModelManager(previousCustomerBook, previousPhoneBook, previousOrderBook,
                previousScheduleBook, previousArchivedOrderBook, new UserPrefs());
        toRedoCommand.save(oldModel);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toRedoCommand.getSuccessMessage()));
    }

}
