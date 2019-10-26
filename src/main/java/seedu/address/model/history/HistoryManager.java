package seedu.address.model.history;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyAddressBook;

public class HistoryManager {
    public static Stack<Command> commands = new Stack<>();
    public static Stack<ReadOnlyAddressBook> addressBooks = new Stack<>();
    public static Stack<Command> undoneCommands = new Stack<>();
    public static Stack<ReadOnlyAddressBook> undoneAddressBooks = new Stack<>();
    
    public HistoryManager() {}
    
    public Command getLatestCommand() {
        return commands.peek();
    }
    
    public boolean isUndoneEmpty() {
        return commands.empty();
    }
    
    public boolean isRedoneEmpty() {
        return undoneCommands.empty();
    }
}
