package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Adds a tag to a module.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds the specified tag to the specified module. "
        + "Parameters: "
        + "MODULE CODE "
        + "TAG_NAME \n"
        + "Example: "
        + "tag CS3230 exchange";

    public static final String MESSAGE_SUCCESS_TAG_ADDED = "New tag created: %1$s \n" + "Tag added to module";
    public static final String MESSAGE_SUCCESS = "Tag added to module";
    public static final String MESSAGE_EXISTING_TAG = "This tag is already attached to this module";
    public static final String MESSAGE_INVALID_MODULE_CODE = "The provided module code is invalid";

    private final String tagName;
    private final String moduleCode;
    private boolean newTagCreated = false;

    /**
     * Creates an {@code AddTagCommand} to add a tag with the given name to the module of the given module code.
     * @param tagName
     */
    public AddTagCommand(String tagName, String moduleCode) {
        requireAllNonNull(tagName, moduleCode);
        this.tagName = tagName;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        Tag toAdd = getTagToAdd(uniqueTagList);
        Module module;
        try {
            module = getTargetModule(activeStudyPlan);
        } catch (ModuleNotFoundException exception) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (moduleContainsTag(module, toAdd)) {
            throw new CommandException(MESSAGE_EXISTING_TAG);
        }

        module.addTag(toAdd);

        if (newTagCreated) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_TAG_ADDED, toAdd));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private boolean moduleContainsTag(Module module, Tag tag) {
        return module.getTags().contains(tag);
    }

    private Module getTargetModule(StudyPlan activeStudyPlan) throws ModuleNotFoundException {
        UniqueModuleList uniqueModuleList = activeStudyPlan.getModules();
        Iterator<Module> moduleIterator = uniqueModuleList.iterator();
        while (moduleIterator.hasNext()) {
            Module currentModule = moduleIterator.next();
            if (currentModule.getModuleCode().toString().equals(moduleCode)) {
                return currentModule;
            }
        }
        throw new ModuleNotFoundException();
    }

    private Tag getTagToAdd(UniqueTagList uniqueTagList) {
        boolean tagExists = uniqueTagList.getMapTags().containsKey(tagName);
        Tag toAdd;
        if (!tagExists) {
            toAdd = createNewTag(tagName, uniqueTagList);
        } else {
            Tag existingTag = uniqueTagList.getMapTags().get(tagName);
            toAdd = existingTag;
        }
        return toAdd;
    }

    private UserTag createNewTag(String tagName, UniqueTagList uniqueTagList) {
        UserTag toCreate = new UserTag(tagName);
        uniqueTagList.addUserTag(toCreate);
        newTagCreated = true;
        return toCreate;
    }

}
