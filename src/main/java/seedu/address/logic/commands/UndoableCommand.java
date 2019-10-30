package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyDataBook<Customer> previousCustomerBook;
    private ReadOnlyDataBook<Order> previousOrderBook;
    private ReadOnlyDataBook<Phone> previousPhoneBook;
    private ReadOnlyDataBook<Schedule> previousScheduleBook;
    private ReadOnlyDataBook<Order> previousArchivedOrderBook;

    protected abstract CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                            UndoRedoStack undoRedoStack) throws CommandException;

    /**
     * Stores the current state of {@code model#customerBook}.
     */
    private void saveCustomerBookSnapshot(Model model) {
        requireNonNull(model);
        this.previousCustomerBook = new DataBook<>(model.getCustomerBook());
    }

    /**
     * Stores the current state of {@code model#phoneBook}.
     */
    private void savePhoneBookSnapshot(Model model) {
        requireNonNull(model);
        this.previousPhoneBook = new DataBook<>(model.getPhoneBook());
    }

    /**
     * Stores the current state of {@code model#orderBook}.
     */
    private void saveOrderBookSnapshot(Model model) {
        requireNonNull(model);
        this.previousOrderBook = new DataBook<>(model.getOrderBook());
    }

    /**
     * Stores the current state of {@code model#scheduleBook}.
     */
    private void saveScheduleBookSnapshot(Model model) {
        requireNonNull(model);
        this.previousScheduleBook = new DataBook<>(model.getScheduleBook());
    }


    /**
     * Stores the current state of {@code model#archivedOrderBook}.
     */
    private void saveArchivedOrderBookSnapshot(Model model) {
        requireNonNull(model);
        this.previousArchivedOrderBook = new DataBook<>(model.getArchivedOrderBook());
    }



    /**
     * Reverts all the books to the state before this command
     * was executed and updates all the list to
     * show all customers, phones, orders, schedules and archived.
     */
    protected final void undo(Model model) {
        requireAllNonNull(model, previousCustomerBook, previousPhoneBook,
                previousOrderBook, previousScheduleBook, previousArchivedOrderBook);

        model.setCustomerBook(previousCustomerBook);
        model.setPhoneBook(previousPhoneBook);
        model.setOrderBook(previousOrderBook);
        model.setScheduleBook(previousScheduleBook);
        model.setArchivedOrderBook(previousArchivedOrderBook);

        model.updateFilteredCustomerList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        model.updateFilteredPhoneList(Model.PREDICATE_SHOW_ALL_PHONES);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDER);
        model.updateFilteredScheduleList(Model.PREDICATE_SHOW_ALL_SCHEDULE);
        model.updateFilteredArchivedOrderList(Model.PREDICATE_SHOW_ALL_ORDER);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo(Model model) {
        requireNonNull(model);
        try {
            executeUndoableCommand(model, new CommandHistory(), new UndoRedoStack());
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }

        model.updateFilteredCustomerList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        model.updateFilteredPhoneList(Model.PREDICATE_SHOW_ALL_PHONES);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDER);
        model.updateFilteredScheduleList(Model.PREDICATE_SHOW_ALL_SCHEDULE);
        model.updateFilteredArchivedOrderList(Model.PREDICATE_SHOW_ALL_ORDER);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {

        saveCustomerBookSnapshot(model);
        savePhoneBookSnapshot(model);
        saveOrderBookSnapshot(model);
        saveScheduleBookSnapshot(model);
        saveArchivedOrderBookSnapshot(model);

        return executeUndoableCommand(model, commandHistory, undoRedoStack);
    }

}
