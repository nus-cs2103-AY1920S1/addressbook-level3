package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.UserTag;

/**
 * Removes a tag from all modules. Only user-created tags can be removed.
 */
public class RemoveTagFromAllCommand extends Command {

    public static final String COMMAND_WORD = "removeall";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing a tag from all modules";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Removes the tag with the specified tag name "
            + "from all modules "
            + "Parameters: "
            + "TAG_NAME \n"
            + "Example: "
            + "removeall exchange";

    public static final String MESSAGE_SUCCESS = "Tag %1$s hass been removed from all modules";
    public static final String MESSAGE_TAG_NOT_FOUND_IN_STUDY_PLAN = "There is no [%1$s] tag in this study plan";
    public static final String MESSAGE_TAG_NOT_FOUND_IN_MODULES = "There is no [%1$s] tag in any module";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be removed";

    private final String tagName;

    /**
     * Creates an {@code RemoveTagFromAllCommand} to remove the tag with the given name from all modules.
     *
     * @param tagName
     */
    public RemoveTagFromAllCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!UserTag.isValidTagName(tagName)) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        if (!model.activeSpContainsModuleTag(tagName)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND_IN_STUDY_PLAN, tagName));
        }

        UserTag toRemove = (UserTag) model.getModuleTagFromActiveSp(tagName);

        boolean removed = model.removeTagFromAllModulesInActiveSp(toRemove);

        if (!removed) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND_IN_MODULES, tagName));
        }

        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagFromAllCommand // instanceof handles nulls
                && tagName.equals(((RemoveTagFromAllCommand) other).tagName)); // state check
    }

}
