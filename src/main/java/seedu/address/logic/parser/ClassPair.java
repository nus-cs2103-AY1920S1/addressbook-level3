// @@author sreesubbash
package seedu.address.logic.parser;

/**
 * Pair class helps to hold two classes together.
 */
public class ClassPair {

    private Class command;
    private Class parser;

    /**
     * A simple constructor to create a pair of classes
     * @param command
     * @param parser
     */
    public ClassPair(Class command, Class parser) {
        this.command = command;
        this.parser = parser;
    }

    /**
     * Gets command class from pair.
     * @return Command Class
     */
    public Class getCommand() {
        return this.command;
    }

    /**
     * Gets parser class from pair.
     * @return Parser Class
     */
    public Class getParser() {
        return this.parser;
    }
}
