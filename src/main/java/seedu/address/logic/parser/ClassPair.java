package seedu.address.logic.parser;

/**
 * Pair class helps to hold two classes together.
 */
public class ClassPair {

    private Class command;
    private Class parser;

    public ClassPair(Class command, Class parser) {
        this.command = command;
        this.parser = parser;
    }

    public Class getCommand() {
        return this.command;
    }

    public Class getParser() {
        return this.parser;
    }
}
