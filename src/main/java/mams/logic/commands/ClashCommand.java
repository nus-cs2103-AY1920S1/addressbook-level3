package mams.logic.commands;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.module.Module;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ClashCommand extends Command {

    public static final String COMMAND_WORD = "clash";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks timetable clashes "
            + "by the module codes or appeal number or student's matric number. \n";

    public static final String MESSAGE_CLASH_DETECTED = "Timetable clash detected: %1$s";
    public static final String MESSAGE_CLASH_NOT_DETECTED = "There is no timetable clash.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Please enter 2 valid module codes.";

    private final String moduleA;
    private final String moduleB;

    public ClashCommand(String moduleA, String moduleB) {
        requireNonNull(moduleA);
        requireNonNull(moduleB);

        this.moduleA = moduleA;
        this.moduleB = moduleB;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        List<Module> modulesToCheckListA = lastShownList.stream()
                .filter(m -> m.getModuleCode().equals(moduleA)).collect(Collectors.toList());
        Module moduleToCheckA = modulesToCheckListA.get(0);
        List<Module> modulesToCheckListB = lastShownList.stream()
                .filter(m -> m.getModuleCode().equals(moduleB)).collect(Collectors.toList());
        Module moduleToCheckB = modulesToCheckListB.get(0);

        if (hasClash(moduleToCheckA, moduleToCheckB)) {
            return new CommandResult(MESSAGE_CLASH_DETECTED);
        } else {
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
        }
    }

    private boolean hasClash(Module moduleToCheckA, Module moduleToCheckB) {
        int[] timeTableA = moduleToCheckA.getTimeSlotToIntArray();
        int[] timeTableB = moduleToCheckB.getTimeSlotToIntArray();
        int i = 0;
        int j = 0;
        while (i < timeTableA.length && j < timeTableB.length) {
            if (timeTableA[i] == timeTableB[j]) {
                return true;
            } else if (timeTableA[i] < timeTableB[j]) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClashCommand)) {
            return false;
        }

        // state check
        ClashCommand c = (ClashCommand) other;
        return moduleA.equals(c.moduleA)
                && moduleB.equals(c.moduleB);
    }
}
