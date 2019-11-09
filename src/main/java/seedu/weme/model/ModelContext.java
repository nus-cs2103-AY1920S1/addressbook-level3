package seedu.weme.model;

/**
 * Determines the current context of the application.
 */
public enum ModelContext {
    // List of all contexts in the application.
    CONTEXT_VIEW("view"),
    CONTEXT_MEMES("memes"),
    CONTEXT_TEMPLATES("templates"),
    CONTEXT_CREATE("create"),
    CONTEXT_STATISTICS("statistics"),
    CONTEXT_EXPORT("export"),
    CONTEXT_IMPORT("import"),
    CONTEXT_PREFERENCES("preferences");

    private final String contextName;

    ModelContext(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }
}
