package seedu.address.logic.commands.datamanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Clears all user-created tags from all modules in the active study plan.
 */
public class RemoveAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "removealltags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Removes all user-created tags from all modules "
            + "Example: "
            + "removealltags";

    public static final String MESSAGE_SUCCESS = "All the tags that you have created have been removed from "
            + "all modules";
    public static final String MESSAGE_NO_TAGS_TO_REMOVE = "You have not created any tags to be removed";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        HashMap<String, Module> moduleHashMap = model.getModulesFromActiveSp();
        Set<String> moduleCodes = moduleHashMap.keySet();
        boolean anyRemoved = false;
        for (String moduleCode : moduleCodes) {
            UniqueTagList modelTagList = model.getModuleTagsFromActiveSp(moduleCode);
            List<Tag> defaultTags = modelTagList.asUnmodifiableObservableList().stream()
                    .filter(tag -> tag.isDefault()).collect(Collectors.toList());
            if (defaultTags.size() < modelTagList.asUnmodifiableObservableList().size()) {
                anyRemoved = true;
            }
            modelTagList.setTags(defaultTags);
        }

        if (!anyRemoved) {
            throw new CommandException(MESSAGE_NO_TAGS_TO_REMOVE);
        }

        model.addToHistory();

        return new CommandResult(MESSAGE_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveAllTagsCommand); // instanceof handles nulls and type check
    }

}
