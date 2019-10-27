package mams.logic;

import java.util.Objects;

/**
 * Class representing the inputs (command entered in {@code CommandBox} and outputs (feedback to user,
 * shown in {@code ResultBox}) of a single command.
 */
public class InputOutput {
    private final String input;
    private final String output;

    /**
     * @param input the command input of the user
     * @param output the feedback given to the user
     */
    public InputOutput(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, output);
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
                && getOutput().equals(other.getOutput());
    }

    @Override
    public String toString() {
        return input + "\n" + output;
    }
}
