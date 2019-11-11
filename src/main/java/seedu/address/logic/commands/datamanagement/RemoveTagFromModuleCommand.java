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
import seedu.address.model.tag.UserTag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a {@code Tag} from a {@code Module}.
 */
public class RemoveTagFromModuleCommand extends Command {

    public static final String COMMAND_WORD = "removetag";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing a tag from a module";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the specified tag from the specified module "
            + "Parameters: "
            + "MODULE_CODE "
            + "TAG_NAME \n"
            + "Example: "
            + "removetag CS3230 exchange";

    public static final String MESSAGE_SUCCESS = "Tag %1$s removed from %2$s";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "This module does not exist.";
    public static final String MESSAGE_TAG_NOT_FOUND = "The module %1$s does not have the tag [%2$s]";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be removed";

    private static final Logger logger = LogsCenter.getLogger(RemoveTagFromModuleCommand.class);

    private final String tagName;
    private final String moduleCode;

    /**
     * @param tagName    The name of the tag.
     *                   Creates an {@code RemoveTagFromModuleCommand} to move a tag with the given name from
     *                   the specified module.
     * @param moduleCode The module code of the module from which the tag is to be deleted.
     */

    public RemoveTagFromModuleCommand(String moduleCode, String tagName) {
        requireAllNonNull(tagName, moduleCode);
        this.tagName = tagName;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (!model.isValidModuleCode(this.moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        Tag toRemove;
        try {
            toRemove = model.getModuleTagFromActiveSp(tagName);
        } catch (TagNotFoundException exception) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, moduleCode, tagName));
        }

        if (toRemove.isDefault()) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        logger.info("Found " + toRemove + " in " + moduleCode);

        boolean removed = model.removeTagFromModuleInActiveSp((UserTag) toRemove, moduleCode);

        if (!removed) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, moduleCode, tagName));
        }
        model.addToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove, moduleCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagFromModuleCommand // instanceof handles nulls
                && tagName.equals(((RemoveTagFromModuleCommand) other).tagName)
                && moduleCode.equals(((RemoveTagFromModuleCommand) other).moduleCode)); // state check
    }

}
