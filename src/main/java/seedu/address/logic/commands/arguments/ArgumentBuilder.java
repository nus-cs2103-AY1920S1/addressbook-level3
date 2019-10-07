package seedu.address.logic.commands.arguments;

public abstract class ArgumentBuilder<T> {

    private final String description;

    private Argument<T> argument;

    ArgumentBuilder(String description) {
        this.description = description;
    }

    public T getValue() {
        return this.argument.getValue();
    }

    public Argument<T> build() {
        this.argument = argumentBuild();
        return this.argument;
    }

    abstract Argument<T> argumentBuild();

    String getDescription() {
        return description;
    }
}
