package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Deletes a tag completely from the study plan.
 */
public class DeleteTagCommand extends Command {

    // incomplete execute method

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Deletes the tag with the specified tag name "
        + "Parameters: "
        + PREFIX_TAG + "TAG_NAME \n"
        + "Example: "
        + "delete t/exchange";

    public static final String MESSAGE_SUCCESS = "Tag deleted: %1$s";
    public static final String MESSAGE_TAG_CANNOT_BE_FOUND = "This tag does not exist";
    public static final String MESSAGE_INVALID_TAG_NAME = UserTag.MESSAGE_CONSTRAINTS;

    private final String tagName;

    /**
     * Creates an {@code DeleteTagCommand} to delete the tag with the given name.
     * @param tagName
     */
    public DeleteTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!UserTag.isValidTagName(tagName)) {
            throw new CommandException(MESSAGE_INVALID_TAG_NAME);
        }

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        if (!uniqueTagList.containsTagWithName(tagName)) {
            throw new CommandException(MESSAGE_TAG_CANNOT_BE_FOUND);
        }

        UserTag toDelete = (UserTag) uniqueTagList.getTag(tagName);
        // delete from mega-list
        uniqueTagList.remove(toDelete);

        // delete from list in every module
        HashMap<String, Module> moduleHashMap = activeStudyPlan.getModules();
        Set<String> moduleCodes = moduleHashMap.keySet();
        for (String moduleCode: moduleCodes) {
            Module currentModule = moduleHashMap.get(moduleCode);
            UniqueTagList moduleTagList = currentModule.getTags();
            if (moduleTagList.containsTagWithName(tagName)) {
                moduleTagList.remove((UserTag)moduleTagList.getTag(tagName));
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

}
