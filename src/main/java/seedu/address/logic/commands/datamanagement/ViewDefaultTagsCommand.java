package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.ui.ResultViewType;

/**
 * Shows all default tags.
 */
public class ViewDefaultTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewdefaulttags";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing default tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all default tags. "
            + "Example: "
            + "viewdefaulttags";

    public static final String MESSAGE_SUCCESS = "All default tags shown";

    /**
     * Creates an {@code ViewDefaultTagsCommand} to show all default tags in the active study plan.
     */
    public ViewDefaultTagsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniqueTagList uniqueTagList = model.getTagsFromActiveSp();

        Predicate<Tag> isTagDefault = tag -> (tag.isDefault());

        ObservableList<Tag> defaultTags = uniqueTagList.asUnmodifiableObservableList().filtered(isTagDefault);

        return new CommandResult(MESSAGE_SUCCESS, ResultViewType.TAG, defaultTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewDefaultTagsCommand); // instanceof handles nulls and type check
    }


}
