package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Renames a tag
 */
public class RenameTagCommand extends Command {

    public static final String COMMAND_WORD = "renametag";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Renaming an existing tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames the tag with the specified original name "
            + "with the specified new name. "
            + "Parameters: "
            + "ORIGINAL_TAG_NAME "
            + "NEW_TAG_NAME \n"
            + "Example: "
            + "renametag exchange SEP";

    public static final String MESSAGE_SUCCESS = "Tag [%1$s] renamed to [%2$s]";
    public static final String MESSAGE_TAG_NOT_FOUND = "There is no [%1$s] tag in this study plan";
    public static final String MESSAGE_SAME_TAG_NAME = "[%1$s] and [%2$s] are the same.";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be renamed";
    public static final String MESSAGE_INVALID_TAG_NAME = "You cannot rename a tag to a default tag name";

    private static final Logger logger = LogsCenter.getLogger(RenameTagCommand.class);

    private final String originalTagName;
    private final String newTagName;

    /**
     * Creates an {@code RenameTagCommand} to rename a tag with the given original name to the given new name.
     *
     * @param originalTagName The original name of the tag.
     * @param newTagName      The new name of the tag.
     */
    public RenameTagCommand(String originalTagName, String newTagName) {
        requireAllNonNull(originalTagName, newTagName);
        this.originalTagName = originalTagName;
        this.newTagName = newTagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        boolean tagExists = model.activeSpContainsModuleTag(originalTagName);
        if (!tagExists) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, originalTagName));
        }

        Tag targetTag = model.getModuleTagFromActiveSp(originalTagName);
        if (targetTag.isDefault()) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        UserTag toRename = (UserTag) targetTag;

        logger.info("Found " + toRename + " in active study plan");

        if (originalTagName.compareToIgnoreCase(newTagName) == 0) {
            throw new CommandException(String.format(MESSAGE_SAME_TAG_NAME, originalTagName, newTagName));
        }

        if (model.activeSpContainsModuleTag(newTagName)) {
            Tag replacement = model.getModuleTagFromActiveSp(newTagName);
            logger.info("Found " + replacement + " in active study plan. Replacing "
                    + toRename + " with " + replacement);
            if (replacement.isDefault()) {
                throw new CommandException(MESSAGE_INVALID_TAG_NAME);
            }
            model.replaceTagInActiveSp(toRename, replacement);
        } else {
            toRename.rename(newTagName);
            UniqueTagList modelTags = model.getModuleTagsFromActiveSp();
            modelTags.updateTagMaps(originalTagName, newTagName);
        }
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, originalTagName, newTagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RenameTagCommand // instanceof handles nulls
                && originalTagName.equals(((RenameTagCommand) other).originalTagName)
                && newTagName.equals(((RenameTagCommand) other).newTagName)); // state check
    }

}
