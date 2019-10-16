package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.module.Module;

/**
 * Checks clashes in timetables.
 */
public class ClashModCommand extends ClashCommand {

    private final String moduleA;
    private final String moduleB;

    public ClashModCommand(String moduleA, String moduleB) {
        requireNonNull(moduleA);
        requireNonNull(moduleB);

        this.moduleA = moduleA;
        this.moduleB = moduleB;
        super.clashingSlots = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        List<Module> modulesToCheckListA = lastShownList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleA)).collect(Collectors.toList());

        if (modulesToCheckListA.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        List<Module> modulesToCheckListB = lastShownList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleB)).collect(Collectors.toList());

        if (modulesToCheckListB.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }


        Module moduleToCheckA = modulesToCheckListA.get(0);
        Module moduleToCheckB = modulesToCheckListB.get(0);

        if (getClashingSlots(moduleToCheckA, moduleToCheckB).size() != 0) {
            return new CommandResult(MESSAGE_CLASH_DETECTED + getTimeSlotsToString(moduleToCheckA));
        } else {
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClashModCommand)) {
            return false;
        }

        // state check
        ClashModCommand c = (ClashModCommand) other;
        return moduleA.equals(c.moduleA)
                && moduleB.equals(c.moduleB);
    }
}
