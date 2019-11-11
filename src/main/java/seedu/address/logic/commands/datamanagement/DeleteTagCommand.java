package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.UserTag;

/**
 * Deletes a module tag completely from the study plan.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Deleting a module tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Deletes the module tag with the specified tag name "
            + "Parameters: "
            + "TAG_NAME \n"
            + "Example: "
            + "deletetag exchange";

    public static final String MESSAGE_SUCCESS = "Tag %1$s has been deleted";
    public static final String MESSAGE_TAG_NOT_FOUND = "There is no [%1$s] tag in this study plan";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be deleted";

    private static final Logger logger = LogsCenter.getLogger(DeleteTagCommand.class);

    private final String tagName;

    /**
     * Creates an {@code DeleteTagCommand} to delete the module tag with the given name.
     *
     * @param tagName
     */
    public DeleteTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (!UserTag.isValidTagName(tagName)) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        assert !DefaultTagType.contains(tagName);

        if (!model.activeSpContainsModuleTag(tagName)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagName));
        }

        UserTag toDelete = (UserTag) model.getModuleTagFromActiveSp(tagName);

        logger.info("Found " + toDelete + " in active study plan");

        int originalSize = model.getModuleTagsFromActiveSp().asUnmodifiableObservableList().size();
        model.deleteModuleTagFromActiveSp(toDelete);
        int newSize = model.getModuleTagsFromActiveSp().asUnmodifiableObservableList().size();
        assert newSize == originalSize - 1;

        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && tagName.equals(((DeleteTagCommand) other).tagName)); // state check
    }

}
