package seedu.address.logic.commands.arguments;

public class StringArgumentBuilder extends ArgumentBuilder<String> {

    StringArgumentBuilder(String description) {
        super(description);
    }

    @Override
    Argument<String> argumentBuild() {
        return new StringArgument(this);
    }
}
