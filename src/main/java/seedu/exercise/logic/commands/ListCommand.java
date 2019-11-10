package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.exercise.model.Model;
import seedu.exercise.ui.ListResourceType;

/**
 * Lists all exercises in the exercise book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List items.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "LIST_TYPE" + "\t"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "schedule";
    public static final String MESSAGE_SUCCESS = "Listed all %1$s items";

    private ListResourceType listResourceType;

    public ListCommand(ListResourceType listResourceType) {
        requireNonNull(listResourceType);
        this.listResourceType = listResourceType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, listResourceType.toString().toLowerCase()),
                listResourceType);
    }

}
