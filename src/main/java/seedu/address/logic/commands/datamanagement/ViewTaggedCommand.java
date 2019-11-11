package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.ui.ResultViewType;

/**
 * Shows all modules attached to specific tags.
 */
public class ViewTaggedCommand extends Command {

    public static final String COMMAND_WORD = "viewtagged";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing modules with a specified tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all modules attached to specific tags. "
            + "Parameters: "
            + "TAG_NAME... \n"
            + "Example: "
            + "viewtagged core completed";

    public static final String MESSAGE_SUCCESS = "All modules with the specified tags shown";
    public static final String MESSAGE_NO_MODULES_FOUND = "There are no modules attached to this tag";

    private final String[] tagNames;

    /**
     * Creates an {@code ViewTaggedCommand} to show all modules with the specified tags.
     */
    public ViewTaggedCommand(String... tagNames) {
        Arrays.stream(tagNames).forEach(tagName -> requireNonNull(tagName));
        this.tagNames = tagNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        HashMap<String, Module> moduleHashMap = model.getModulesFromActiveSp();

        UniqueModuleList allMatchingModules = getAllMatchingModules(moduleHashMap);

        if (allMatchingModules.asUnmodifiableObservableList().size() == 0) {
            throw new CommandException(MESSAGE_NO_MODULES_FOUND);
        }

        return new CommandResult(MESSAGE_SUCCESS, ResultViewType.MODULE,
                allMatchingModules.asUnmodifiableObservableList());
    }

    private UniqueModuleList getAllMatchingModules(HashMap<String, Module> moduleHashMap) {
        UniqueModuleList allMatchingModules = new UniqueModuleList();
        for (String tagName : tagNames) {
            List<Module> matchingModules = getMatchingModules(tagName, moduleHashMap);
            if (allMatchingModules.asUnmodifiableObservableList().size() == 0) {
                allMatchingModules.setModules(matchingModules);
            } else {
                allMatchingModules.setModules(allMatchingModules.asUnmodifiableObservableList()
                        .stream()
                        .filter(matchingModules::contains)
                        .collect(Collectors.toList()));
            }
        }
        return allMatchingModules;
    }

    private List<Module> getMatchingModules(String tagName, HashMap<String, Module> moduleHashMap) {
        Set<String> moduleNames = moduleHashMap.keySet();
        List<Module> matchingModules = new ArrayList<Module>();
        for (String moduleName : moduleNames) {
            Module currentModule = moduleHashMap.get(moduleName);
            boolean matches = checkMatch(currentModule, tagName);
            if (matches) {
                matchingModules.add(currentModule);
            }
        }
        return matchingModules;
    }

    /**
     * Checks if there are any tags attached to the current module that has the given tag name.
     *
     * @param currentModule The module with an existing list of tags.
     * @param tagName       The name of the tag that is to be checked.
     * @return True if the module has a tag with the given name.
     */
    private boolean checkMatch(Module currentModule, String tagName) {
        UniqueTagList tags = currentModule.getTags();
        for (Tag tag : tags) {
            boolean match = (tag.getTagName().compareToIgnoreCase(tagName) == 0);
            if (match) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTaggedCommand // instanceof handles nulls
                && tagNames.length == ((ViewTaggedCommand) other).tagNames.length // state check
                && Arrays.asList(tagNames).containsAll(Arrays.asList(((ViewTaggedCommand) other).tagNames)));
    }

}
