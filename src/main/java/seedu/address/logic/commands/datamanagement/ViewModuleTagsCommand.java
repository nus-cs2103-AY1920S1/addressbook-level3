package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.ui.ResultViewType;

/**
 * Shows all tags attached to a specific module.
 */
public class ViewModuleTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewtags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all tags attached to a specific module. "
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE CODE \n"
        + "Example: "
        + "viewtags t/CS3230";

    //public static final String MESSAGE_SUCCESS = "All tags for the module shown \n%1$s";
    public static final String MESSAGE_SUCCESS = "All tags for the module shown";
    private final String moduleCode;

    /**
     * Creates an {@code ViewModuleTagsCommand} to show all tags attached to the given module.
     */
    public ViewModuleTagsCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniqueTagList tags = model.getModuleTagsFromActiveSp(moduleCode);

        final String stringOfTags = tags.asUnmodifiableObservableList().stream()
            .map(item -> item.toString())
            .collect(joining("\n"));

        return new CommandResult(MESSAGE_SUCCESS, ResultViewType.TAG, tags.asUnmodifiableObservableList());
        //return new CommandResult(String.format(MESSAGE_SUCCESS, stringOfTags));
    }

}
