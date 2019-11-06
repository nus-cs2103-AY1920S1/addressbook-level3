package seedu.address.model.history;

import java.util.List;
import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.training.Training;

/**
 * Represents the history of commands and states of address books.
 */
public class HistoryManager {
    private static Stack<Command> commands = new Stack<>();
    private static Stack<ReadOnlyAthletick> addressBooks = new Stack<>();
    private static Stack<Command> undoneCommands = new Stack<>();
<<<<<<< HEAD
    private static Stack<ReadOnlyAddressBook> undoneAddressBooks = new Stack<>();
    private static Stack<List<Training>> trainingLists = new Stack<>();
    private static Stack<List<Training>> undoneTrainingLists = new Stack<>();
=======
    private static Stack<ReadOnlyAthletick> undoneAddressBooks = new Stack<>();
    private static Stack<Training> undoneTrainingLists = new Stack<>();
>>>>>>> 8038f5b073ce72e9a18558307eb1ee3db3705cf0
    public HistoryManager() {}
    public Command getLatestCommand() {
        return commands.peek();
    }
    public static Stack<Command> getCommands() {
        return commands;
    }
    public static Stack<ReadOnlyAthletick> getAddressBooks() {
        return addressBooks;
    }
    public static Stack<Command> getUndoneCommands() {
        return undoneCommands;
    }
    public static Stack<ReadOnlyAthletick> getUndoneAddressBooks() {
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
