package seedu.address.history;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyAddressBook;

public class HistoryManager {
    public static Stack<Command> commands = new Stack<>();
    public static Stack<ReadOnlyAddressBook> addressBooks = new Stack<>();
    public Stack<Command> undoableCommands = new Stack<>();
    public Stack<ReadOnlyAddressBook> undoableAddressBooks = new Stack<>();
    
    public HistoryManager() {}
    
    public Command getLatestCommand() {
        return commands.peek();
    }
    
    public boolean isEmpty() {
        return commands.empty();
    }
}
