package seedu.address.logic.parser;

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