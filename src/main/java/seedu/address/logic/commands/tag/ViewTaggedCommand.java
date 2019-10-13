package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Shows all modules attached to specific tags.
 */
public class ViewTaggedCommand extends Command {

    public static final String COMMAND_WORD = "viewtagged";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all modules attached to specific tags. "
        + "Parameters: "
        + "TAG_NAME... \n"
        + "Example: "
        + "viewtagged core completed";

    public static final String MESSAGE_SUCCESS = "All modules with the specified tags shown %1$s.";

    private final String[] tagNames;

    /**
     * Creates an {@code ViewTaggedCommand} to show all modules with the specified tags.
     */
    public ViewTaggedCommand(String... tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        HashMap<String, Module> moduleHashMap = activeStudyPlan.getModules();

        Set<Module> allMatchingModules = getAllMatchingModules(moduleHashMap);

        return new CommandResult(String.format(MESSAGE_SUCCESS, allMatchingModules));
    }

    private Set<Module> getMatchingModules(String tagName, HashMap<String, Module> moduleHashMap) {
        Set<String> moduleNames = moduleHashMap.keySet();
        Set<Module> matchingModules = new HashSet<Module>();
        for (String moduleName: moduleNames) {
            Module currentModule = moduleHashMap.get(moduleName);
            boolean matches = checkMatch(currentModule, tagName);
            if (matches) {
                matchingModules.add(currentModule);
            }
        }
        return matchingModules;
    }

    private Set<Module> getAllMatchingModules(HashMap<String, Module> moduleHashMap) {
        Set<Module> allMatchingModules = new HashSet<Module>();
        for (String tagName: tagNames) {
            Set<Module> matchingModules = getMatchingModules(tagName, moduleHashMap);
            if (allMatchingModules.size() == 0) {
                allMatchingModules.addAll(matchingModules);
            } else {
                allMatchingModules = allMatchingModules.stream()
                    .filter(matchingModules::contains)
                    .collect(Collectors.toSet());
            }
        }
        return allMatchingModules;
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
