package seedu.address.model.history;

import java.util.List;
import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.model.Athletick;
import seedu.address.model.Model;
import seedu.address.model.Performance;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.TrainingManager;
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
    /**
     * This initialises the HistoryManager, populating the stacks with the initial states
     * when the application starts.
     * @param model model class of the application
     */
    public void init(Model model) {
        this.addressBooks.push(model.getAthletickDeepCopy());
        this.trainingLists.push(model.getTrainingsDeepCopy(model.getTrainingManager().getTrainings()));
        this.performances.push(model.getPerformanceDeepCopy(model.getPerformance()));
    }
    public Stack<Command> getCommands() {
        return this.commands;
    }
    public Stack<ReadOnlyAthletick> getAddressBooks() {
        return this.addressBooks;
    }
    public Command getLatestCommand() {
        return commands.peek();
    }
    public Command popLatestCommand() {
        return commands.pop();
    }
    public Command popLatestUndoneCommand() {
        return undoneCommands.pop();
    }
    public void pushCommand(Command command) {
        commands.push(command);
    }
    public void pushUndoneCommand(Command undoneCommand) {
        undoneCommands.push(undoneCommand);
    }
    public ReadOnlyAthletick popLatestAthletick() {
        return addressBooks.pop();
    }
    public ReadOnlyAthletick popLatestUndoneAthletick() {
        return undoneAddressBooks.pop();
    }
    public void pushAthletick(ReadOnlyAthletick athletick) {
        addressBooks.push(athletick);
    }
    public void pushUndoneAthletick(ReadOnlyAthletick undoneAthletick) {
        undoneAddressBooks.push(undoneAthletick);
    }
    public void pushTrainingList(List<Training> trainingList) {
        trainingLists.push(trainingList);
    }
    public void pushPerformances(ReadOnlyPerformance performance) {
        performances.push(performance);
    }
    public boolean isUndoneEmpty() {
        return this.commands.empty();
    }
    public boolean isRedoneEmpty() {
        return this.undoneCommands.empty();
    }
    /**
     * reset the athletick state to the state after undo command
     * @param athletick single athletick class of the application
     */
    public void undoAthletickStack(Athletick athletick) {
        ReadOnlyAthletick afterUndoneState = this.addressBooks.peek();
        athletick.resetData(afterUndoneState);
    }
    /**
     * After undo, pops the latest training list from the stack of training lists
     * and push it to the undone stack of training list.
     * Then, resets the state of attendance from the latest training list after undone.
     * @param trainingManager single attendance class of the application
     * @param model single model class of the application
     */
    public void undoTrainingStack(TrainingManager trainingManager, Model model) {
        List<Training> undoneTrainingList = this.trainingLists.pop();
        this.undoneTrainingLists.push(undoneTrainingList);
        List<Training> afterUndoneTrainingList = this.trainingLists.peek();
        trainingManager.resetTrainingList(model.getTrainingsDeepCopy(afterUndoneTrainingList));
    }
    /**
     * After undo, pops the latest performance from the stack of performance
     * and push it to the undone stack of performance.
     * Then, resets the state of performance from the latest performance after undone.
     * @param performance single performance class of the application
     * @param model single model class of application
     */
    public void undoPerformanceStack(Performance performance, Model model) {
        ReadOnlyPerformance undonePerformance = this.performances.pop();
        this.undonePerformances.push(undonePerformance);
        ReadOnlyPerformance afterUndonePerformance = this.performances.peek();
        performance.resetData(model.getPerformanceDeepCopy(afterUndonePerformance));
    }
    /**
     * After redo, pops the latest undone training lists from the stack
     * of undone training lists and push it to the stack of training lists.
     * Then, resets the state of attendance from the latest training list after redo.
     * @param trainingManager single attendance class of the application
     * @param model single model class of application
     */
    public void redoTrainingStack(TrainingManager trainingManager, Model model) {
        List<Training> redoneTrainingLists = model.getTrainingsDeepCopy(this.undoneTrainingLists.pop());
        this.trainingLists.push(redoneTrainingLists);
        trainingManager.resetTrainingList(model.getTrainingsDeepCopy(redoneTrainingLists));
    }
    /**
     * After redo, pops the latest undone performance from the stack
     * of undone performance and push it to the stack of performance.
     * Then, resets the state of performance from the latest performance after redo.
     * @param performance single performance class of the application
     * @param model single model class of the application
     */
    public void redoPerformanceStack(Performance performance, Model model) {
        ReadOnlyPerformance redonePerformance = this.undonePerformances.pop();
        this.performances.push(redonePerformance);
        performance.resetData(model.getPerformanceDeepCopy(redonePerformance));
    }
}

