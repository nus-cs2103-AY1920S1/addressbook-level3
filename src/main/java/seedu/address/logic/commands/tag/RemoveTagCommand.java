package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.tag.exceptions.InvalidTagModificationException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a {@code Tag} from a {@code Module}.
 */
public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Removes the specified tag from the specified module "
        + "Parameters: "
        + "MODULE_CODE "
        + "TAG_NAME \n"
        + "Example: "
        + "remove CS3230 exchange";

    public static final String MESSAGE_SUCCESS = "Tag removed %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "This module does not have the specified tag";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be removed";

    private final String tagName;
    private final String moduleCode;

    /**
     * Creates an {@code RemoveTagCommand} to move a tag with the given name from the specified module.
     * @param tagName The name of the tag.
     * @param moduleCode The module code of the module from which the tag is to be deleted.
     */

    public RemoveTagCommand(String tagName, String moduleCode) {
        requireAllNonNull(tagName, moduleCode);
        this.tagName = tagName;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        HashMap<String, Module> moduleHashMap = activeStudyPlan.getModules();
        Module targetModule = moduleHashMap.get(moduleCode);

        boolean matches = checkMatch(targetModule, tagName);
        if (!matches) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        UniqueTagList uniqueTagList = activeStudyPlan.getTags();
        HashMap<String, Tag> tagHashMap = uniqueTagList.getMapTags();
        Tag toDelete = tagHashMap.get(tagName);
        if (toDelete.isDefault()) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        targetModule.getTags().remove((UserTag) toDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    /**
     * Checks if there are any tags attached to the current module that has the given tag name.
     * @param currentModule The module with an existing list of tags.
     * @param tagName The name of the tag that is to be checked.
     * @return True if the module has a tag with the given name.
     */
    private boolean checkMatch(Module currentModule, String tagName) {
        UniqueTagList tags = currentModule.getTags();
        for (Tag tag: tags) {
            boolean match = tag.getTagName().equals(tagName);
            if (match) {
                return true;
            }
        }
        return false;
    }

}