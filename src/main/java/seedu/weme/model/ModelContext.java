package seedu.weme.model;

/**
 * Determines the current context of the application.
 */
public enum ModelContext {
    // List of all contexts in the application.
    CONTEXT_MEMES("memes"),
    CONTEXT_TEMPLATES("templates"),
    CONTEXT_ARCHIVE("archive"),
    CONTEXT_STATISTICS("statistics"),
    CONTEXT_STORAGE("storage");

    private final String contextName;

    ModelContext(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }
}
