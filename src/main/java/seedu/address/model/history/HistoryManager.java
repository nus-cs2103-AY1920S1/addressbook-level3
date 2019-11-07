package seedu.address.model.history;

import java.util.List;
import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.model.Model;
import seedu.address.model.Performance;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.training.Training;

/**
 * Represents the history of commands and states of address books.
 */
public class HistoryManager {
    private Stack<Command> commands = new Stack<>();
    private Stack<ReadOnlyAthletick> addressBooks = new Stack<>();
    private Stack<Command> undoneCommands = new Stack<>();
    private Stack<ReadOnlyAthletick> undoneAddressBooks = new Stack<>();
    private Stack<List<Training>> trainingLists = new Stack<>();
    private Stack<List<Training>> undoneTrainingLists = new Stack<>();
    private Stack<ReadOnlyPerformance> performances = new Stack<>();
    private Stack<ReadOnlyPerformance> undonePerformances = new Stack<>();
    
    public HistoryManager() {}
    public void init(Model model) {
        this.addressBooks.push(model.getAthletickDeepCopy());
        this.trainingLists.push(model.getTrainingsDeepCopy(model.getAttendance().getTrainings()));
        this.performances.push(model.getPerformanceDeepCopy(model.getPerformance()));
    }
    public Command getLatestCommand() {
        return commands.peek();
    }
    public Stack<Command> getCommands() {
        return this.commands;
    }
    public Stack<ReadOnlyAthletick> getAddressBooks() {
        return this.addressBooks;
    }
    public Stack<Command> getUndoneCommands() {
        return this.undoneCommands;
    }
    public Stack<ReadOnlyAthletick> getUndoneAddressBooks() {
        return this.undoneAddressBooks;
    }
    public Stack<List<Training>> getTrainingLists() {
        return this.trainingLists;
    }
    public Stack<List<Training>> getUndoneTrainingLists() {
        return this.undoneTrainingLists;
    }
    public boolean isUndoneEmpty() {
        return this.commands.empty();
    }
    public boolean isRedoneEmpty() {
        return this.undoneCommands.empty();
    }
    public Stack<ReadOnlyPerformance> getPerformances() {
        return this.performances;
    }
    public Stack<ReadOnlyPerformance> getUndonePerformances() {
        return this.undonePerformances;
    }
}
