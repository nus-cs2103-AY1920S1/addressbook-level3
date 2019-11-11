package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.ui.ResultViewType;

/**
 * Shows a generic list of modules, which can be extended to core modules, focus area modules, etc.
 */
public abstract class ShowCommand extends Command {

    protected String messageSuccess;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        HashMap<String, Module> moduleHashMap = model.getModulesFromActiveSp();
        UniqueModuleList coreModules = getAllModules(moduleHashMap);

        return new CommandResult(messageSuccess, ResultViewType.MODULE,
                coreModules.asUnmodifiableObservableList());
    }

    private UniqueModuleList getAllModules(HashMap<String, Module> moduleHashMap) {
        UniqueModuleList allCoreModules = new UniqueModuleList();
        List<Module> matchingModules = getModules(moduleHashMap);
        allCoreModules.setModules(matchingModules);
        return allCoreModules;
    }

    private List<Module> getModules(HashMap<String, Module> moduleHashMap) {
        Set<String> moduleNames = moduleHashMap.keySet();
        List<Module> matchingModules = new ArrayList<Module>();
        for (String moduleName : moduleNames) {
            Module currentModule = moduleHashMap.get(moduleName);
            boolean matches = checkModule(currentModule);
            if (matches) {
                matchingModules.add(currentModule);
            }
        }
        return matchingModules;
    }

    protected abstract boolean checkModule(Module currentModule);
}
