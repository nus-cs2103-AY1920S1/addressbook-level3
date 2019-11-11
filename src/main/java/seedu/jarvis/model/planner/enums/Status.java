package seedu.jarvis.model.planner.enums;

/**
 * Represents the completion status of a {@code Task}
 */
public enum Status {

    /**
     * Values that the status of a {@code Task} can take
     */
    DONE,
    NOT_DONE;

    /**
     * An icon representation of the status of a {@code Task}
     * @return [✓] if the {@code Task} is done and
     *         [✗] if it is not.
     */
    public String getIcon() {
        return this == DONE ? "[✓]" : "[✗]";
    }
}
