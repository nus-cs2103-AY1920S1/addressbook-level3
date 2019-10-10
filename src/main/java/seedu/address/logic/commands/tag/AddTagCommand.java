package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Adds a tag to a module.
 */
public class AddTagCommand {

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

    private final String tagName;
    private final String moduleCode;

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

        if (!uniqueTagList.getMapTags().containsKey(tagName)) {
            UserTag toCreate = new UserTag(tagName);
            uniqueTagList.addUserTag(toCreate);
            return new CommandResult(String.format(MESSAGE_SUCCESS_TAG_ADDED, toCreate));
        }

        UniqueModuleList uniqueModuleList = activeStudyPlan.getModules();

        return new CommandResult(String.format(MESSAGE_SUCCESS, tagName));
    }


}
