package seedu.address.logic.commands.csvcommand.csvutil;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * A tracker to show user which line in the CSV file was not able to be loaded into Alfred.
 */
public class ErrorTracker {

    private PriorityQueue<Error> errors;

    public ErrorTracker() {
        this.errors = new PriorityQueue<>();
    }

    public ErrorTracker(Error... errors) {
        this.errors = new PriorityQueue<>(Arrays.asList(errors));
    }

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isEmpty() {
        return this.errors.isEmpty();
    }

    /**
     * Converts {@code errors} into a {@code CSV String}.
     */
    public String toCsvString() {
        PriorityQueue<Error> copy = new PriorityQueue<>(this.errors);
        StringBuilder sb = new StringBuilder();
        while (!copy.isEmpty()) {
            sb.append(copy.poll().csvLine).append("\n");
        }
        return sb.toString().stripTrailing();
    }

    @Override
    public String toString() {
        PriorityQueue<Error> copy = new PriorityQueue<>(this.errors);
        StringBuilder sb = new StringBuilder();
        while (!copy.isEmpty()) {
            sb.append(copy.poll().toString()).append("\n");
        }
        return sb.toString().stripTrailing();
    }

    /**
     * Encapsulates an error arisen while parsing a CSV file into {@code Entity} objects.
     */
    public static class Error implements Comparable<Error> {
        private int lineNumber;
        private String csvLine;
        private String cause;

        public Error(int lineNumber, String csvLine, String cause) {
            this.lineNumber = lineNumber;
            this.csvLine = csvLine;
            this.cause = cause;
        }

        @Override
        public int compareTo(Error other) {
            return this.lineNumber - other.lineNumber;
        }

        @Override
        public String toString() {
            return String.format("\tLine %d: %s (Cause: %s)", this.lineNumber, this.csvLine, this.cause);
        }
    }

}
