package seedu.address.model;

import seedu.address.logic.commands.MutatorCommand;

/**
 * Record of the command and data of one atomic commit in the command history. It consists of the
 * {@link MutatorCommand} responsible for the commit and the state of the {@link AddressBook} before the commit.
 */
public class HistoryRecord {
    private final MutatorCommand command;
    private final AddressBook addressBook;

    /**
     * Constructs a HistoryRecord representing the specified command and state. The state is deeply copied and
     * decoupled from the given reference.
     *
     * @param command the command that caused the change in state
     * @param addressBook the state before the execution of the command
     */
    public HistoryRecord(MutatorCommand command, AddressBook addressBook) {
        this.command = command;
        this.addressBook = addressBook.deepCopy();
    }

    /** Returns the command stored in this record */
    public MutatorCommand getCommand() {
        return command;
    }

    /**
     * Returns a read-only reference to the state stored in this record. This method is preferred over
     * {@link #getCopyOfAddressBook()} if the state does not need to be modified as it avoids copying the entire state.
     */
    public ReadOnlyAddressBook getReadOnlyAddressBook() {
        return addressBook;
    }

    /**
     * Returns a deep copy of the state stored in this record. If the state does not need to be modified, it is
     * recommended to use {@link #getReadOnlyAddressBook()} instead.
     */
    public AddressBook getCopyOfAddressBook() {
        return addressBook.deepCopy();
    }

    @Override
    public String toString() {
        return command.getClass().getSimpleName() + ", " + addressBook.toString()
                + " " + addressBook.hashCode();
    }
}
