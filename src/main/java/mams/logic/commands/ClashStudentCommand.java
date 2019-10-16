package mams.logic.commands;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ClashStudentCommand extends ClashCommand {

    private final String matricID;
    private ArrayList<Module> currentModules;

    public ClashStudentCommand(String matricID) {
        requireNonNull(matricID);

        this.matricID = matricID;
        super.clashingSlots = new ArrayList<>();
        currentModules = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        // to get the student from student list.
        List<Student> studentInList = lastShownList.stream()
                .filter(p -> p.getName().fullName.equals(matricID)).collect(Collectors.toList());
        Student studentToCheck = studentInList.get(0);

        // to get the student current modules.
        Set<Tag> currentModulesSet = studentToCheck.getCurrentModules();
        for (Tag currentModule : currentModulesSet) {
            String moduleCode = currentModule.getTagName();
            List<Module> modulesToCheckListA = model.getFilteredModuleList().stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
            Module moduleToCheck = modulesToCheckListA.get(0);
            currentModules.add(moduleToCheck);
        }

        // to check clash in student's current modules.
        for (int i = 0; i < currentModules.size() - 1; i++) {
            for (int j = i + 1; j < currentModules.size(); j++) {
                clashingSlots.addAll(getClashingSlots(currentModules.get(i), currentModules.get(j)));
            }
        }

        if (clashingSlots.size() != 0) {
            return new CommandResult(MESSAGE_CLASH_DETECTED + getTimeSlotsToString(generateTempMod()));
        } else {
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
        }

    }

    private Module generateTempMod() {
        StringBuilder sb = new StringBuilder("");
        for (int slot : clashingSlots) {
            sb.append(slot).append(",");
        }

        return new Module("", "", "", "", sb.toString(),
                "", new HashSet<Tag>());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClashStudentCommand)) {
            return false;
        }

        // state check
        ClashStudentCommand c = (ClashStudentCommand) other;
        return matricID.equals(c.matricID);
    }
}
