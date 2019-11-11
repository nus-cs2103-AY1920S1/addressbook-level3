package mams.logic.history;

import static mams.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.function.Predicate;

import mams.commons.core.time.TimeStamp;

/**
 * Class representing the inputs (command entered in {@code CommandBox} and outputs (feedback to user,
 * shown in {@code ResultBox}) of a single command.
 */
public class InputOutput {
    public static final Predicate<InputOutput> PREDICATE_SHOW_ALL = unused -> true;
    public static final Predicate<InputOutput> PREDICATE_SHOW_ONLY_SUCCESSFUL = InputOutput::checkSuccessful;
    public static final Predicate<InputOutput> PREDICATE_SHOW_ONLY_UNSUCCESSFUL =
        inputOutput -> !inputOutput.checkSuccessful();

    private final String input;
    private final String output;
    private final boolean isExecutionSuccessful;
    private final TimeStamp timeStamp;

    /**
     * @param input the command input of the user
     * @param output the feedback given to the user
     * @param wasExecutionSuccessful whether execution of this command succeeded without errors
     * @param timeStamp time of command execution as a java.util.Date object
     */
    public InputOutput(String input, String output, boolean wasExecutionSuccessful, TimeStamp timeStamp) {
        requireAllNonNull(input, output, timeStamp);
        this.input = input;
        this.output = output;
        this.isExecutionSuccessful = wasExecutionSuccessful;
        this.timeStamp = timeStamp;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getTimeStampAsString() {
        return timeStamp.toString();
    }

    public boolean checkSuccessful() {
        return isExecutionSuccessful;
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, output, isExecutionSuccessful, timeStamp);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof InputOutput)) {
            return false;
        }

        // state check
        InputOutput other = (InputOutput) obj;
        return getInput().equals(other.getInput())
                && getOutput().equals(other.getOutput())
                && isExecutionSuccessful == other.isExecutionSuccessful
                && timeStamp.equals(other.timeStamp);
    }

    @Override
    public String toString() {
        return timeStamp.toString() + "\n"
                + ((checkSuccessful())
                ? "SUCCESSFUL"
                : "UNSUCCESSFUL") + "\n"
                + input + "\n" + output;
    }
}
