package seedu.address.model.history;

import java.util.List;
import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.training.Training;

/**
 * Represents the history of commands and states of address books.
 */
public class HistoryManager {
    private static Stack<Command> commands = new Stack<>();
    private static Stack<ReadOnlyAddressBook> addressBooks = new Stack<>();
    private static Stack<Command> undoneCommands = new Stack<>();
    private static Stack<ReadOnlyAddressBook> undoneAddressBooks = new Stack<>();
    private static Stack<List<Training>> trainingLists = new Stack<>();
    private static Stack<List<Training>> undoneTrainingLists = new Stack<>();
    public HistoryManager() {}
    public Command getLatestCommand() {
        return commands.peek();
    }
    public static Stack<Command> getCommands() {
        return commands;
    }
    public static Stack<ReadOnlyAddressBook> getAddressBooks() {
        return addressBooks;
    }
    public static Stack<Command> getUndoneCommands() {
        return undoneCommands;
    }
    public static Stack<ReadOnlyAddressBook> getUndoneAddressBooks() {
        return undoneAddressBooks;
    }
    public static Stack<List<Training>> getTrainingLists() {
        return trainingLists;
    }
    public static Stack<List<Training>> getUndoneTrainingLists() {
        return undoneTrainingLists;
    }
    public boolean isUndoneEmpty() {
        return commands.empty();
    }
    public boolean isRedoneEmpty() {
        return undoneCommands.empty();
    }
}
