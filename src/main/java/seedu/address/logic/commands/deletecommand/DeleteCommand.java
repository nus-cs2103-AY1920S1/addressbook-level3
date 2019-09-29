package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;

public abstract class DeleteCommand extends Command {

    /* Possible Fields */

    public static final String COMMAND_WORD = "delete";

    protected Id id;

    protected DeleteCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

}
