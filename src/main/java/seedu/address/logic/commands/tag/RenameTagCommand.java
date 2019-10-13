package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.tag.exceptions.InvalidTagModificationException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class RenameTagCommand extends Command {

    public static final String COMMAND_WORD = "renametag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Renames the tag with the specified original name "
        + "with the specified new name. "
        + "Parameters: "
        + "ORIGINAL_TAG_NAME "
        + "NEW_TAG_NAME \n"
        + "Example: "
        + "rename exchange SEP";

    public static final String MESSAGE_SUCCESS = "Tag renamed %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "A tag with the given tag name cannot be found";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be renamed";

    private final String originalTagName;
    private final String newTagName;

    /**
     * Creates an {@code RenameTagCommand} to rename a tag with the given original name to the given new name.
     * @param originalTagName The original name of the tag.
     * @param newTagName The new name of the tag.
     */
    public RenameTagCommand(String originalTagName, String newTagName) {
        requireAllNonNull(originalTagName, newTagName);
        this.originalTagName = originalTagName;
        this.newTagName = newTagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        Tag toRename;
        try {
            toRename = getTagToRename(uniqueTagList);
        } catch (TagNotFoundException e1) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        } catch (InvalidTagModificationException e2) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        ((UserTag) toRename).rename(newTagName);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toRename));
    }

    private Tag getTagToRename(UniqueTagList uniqueTagList) throws TagNotFoundException,
            InvalidTagModificationException {
        boolean tagExists = uniqueTagList.getMapTags().containsKey(originalTagName);
        if (!tagExists) {
            throw new TagNotFoundException();
        }
        Tag targetTag = uniqueTagList.getMapTags().get(originalTagName);
        if (targetTag.isDefault()) {
            throw new InvalidTagModificationException();
        }
        return targetTag;
    }
}
