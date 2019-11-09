package mams.logic.history;

import static java.util.Objects.requireNonNull;

import static mams.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import mams.commons.core.time.TimeStamp;

/** Class that stores the history of all user inputs into MAMS */
public class CommandHistory implements ReadOnlyCommandHistory, FilterOnlyCommandHistory {
    private final ObservableList<InputOutput> inputOutputHistory = FXCollections.observableArrayList();
    private final ObservableList<InputOutput> unmodifiableInputOutputHistory =
            FXCollections.unmodifiableObservableList(inputOutputHistory);
    private final FilteredList<InputOutput> filteredCommandHistory =
            new FilteredList<>(unmodifiableInputOutputHistory);

    public CommandHistory() {}

    /**
     * Initialize from a List of InputOutput objects.
     * @param inputOutputs
     */
    public CommandHistory(List<InputOutput> inputOutputs) {
        requireNonNull(inputOutputs);
        this.inputOutputHistory.addAll(inputOutputs);
    }

    /**
     * Initialize from a {@code ReadonlyCommandHistory}.
     * @param commandHistory
     */
    public CommandHistory(ReadOnlyCommandHistory commandHistory) {
        this(commandHistory.getInputOutputHistory());
    }

    /**
     * Adds the entered input text from the user and the resulting command feedback into a list.
     */
    public void add(String input, String output, boolean wasExecutionSuccessful, TimeStamp timeStamp) {
        requireAllNonNull(input, output, timeStamp);
        inputOutputHistory.add(new InputOutput(input, output, wasExecutionSuccessful, timeStamp));
    }

    /**
     * Returns an unmodifiable view of {@code inputOutputHistory}
     */
    public ObservableList<InputOutput> getInputOutputHistory() {
        return unmodifiableInputOutputHistory;
    }

    /**
     * Counts number of unsuccessful commands in history.
     * @return int count of unsuccessful commands.
     */
    public int getNumberOfUnsuccessfulCommands() {
        return unmodifiableInputOutputHistory.stream()
                .filter(InputOutput.PREDICATE_SHOW_ONLY_UNSUCCESSFUL)
                .mapToInt(inputOutput -> 1)
                .sum();
    }

    @Override
    public ObservableList<InputOutput> getFilteredCommandHistory() {
        return filteredCommandHistory;
    }

    @Override
    public void updateFilteredCommandHistory(Predicate<InputOutput> predicate) {
        requireNonNull(predicate);
        filteredCommandHistory.setPredicate(predicate);
    }

    @Override
    public int hashCode() {
        return inputOutputHistory.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return inputOutputHistory.equals(other.inputOutputHistory)
                && filteredCommandHistory.equals(other.filteredCommandHistory);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (InputOutput inputOutput : unmodifiableInputOutputHistory) {
            sb.append(inputOutput);
            sb.append("\n");
        }
        return sb.toString();
    }
}
