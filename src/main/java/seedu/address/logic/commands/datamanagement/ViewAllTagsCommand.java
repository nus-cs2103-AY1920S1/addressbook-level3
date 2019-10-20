package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.UniqueTagList;

/**
 * Shows all tags in the active study plan.
 */
public class ViewAllTagsCommand extends Command {

    // not in parser yet

    public static final String COMMAND_WORD = "viewalltags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all tags in the study plan. "
            + "Example: "
            + "viewalltags";

    public static final String MESSAGE_SUCCESS = "All tags shown \n%1$s.";

    /**
     * Creates an {@code ViewAllTagsCommand} to show all tags in the active study plan.
     */
    public ViewAllTagsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniqueTagList uniqueTagList = model.getTagsFromActiveSp();

        final String stringOfTags = uniqueTagList.asUnmodifiableObservableList().stream()
            .map(item -> item.toString())
            .collect(joining("\n"));

        return new CommandResult(String.format(MESSAGE_SUCCESS, stringOfTags));
    }

}
