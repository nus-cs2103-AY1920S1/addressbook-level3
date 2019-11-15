package seedu.revision.model.quiz;

/** Enumeration of valid quiz modes. **/
public enum Modes {
    NORMAL("normal"),
    ARCADE("arcade"),
    CUSTOM("custom");

    private String mode;

    Modes(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.mode;
    }
}
