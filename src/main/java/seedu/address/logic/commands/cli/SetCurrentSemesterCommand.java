package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Iterator;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Sets a semester as the current semester
 */
public class SetCurrentSemesterCommand extends Command {
    public static final String COMMAND_WORD = "setcurrent";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the given semester as the current one. "
            + "Semesters before the specified semester will be locked and therefore modules cannot"
            + " be added or removed from them.\n"
            + "Parameters: "
            + PREFIX_SEMESTER + "SEMESTER\n";

    public static final String MESSAGE_SUCCESS = "Semester %1$s has been set as the current semester";

    private final SemesterName sem;

    public SetCurrentSemesterCommand(SemesterName sem) {
        requireNonNull(sem);
        this.sem = sem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setSemester(sem);
        addCompletedTags(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem));
    }

    /**
     * Adds the DefaultTag of type Completed to all modules in semesters prior to the newly set current semester.
     */
    private void addCompletedTags(Model model) {
        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueSemesterList uniqueSemesterList = activeStudyPlan.getSemesters();
        Iterator<Semester> semesterIterator = uniqueSemesterList.iterator();
        while (semesterIterator.hasNext()) {
            Semester semester = semesterIterator.next();
            if (semester.getSemesterName().compareTo(sem) < 0) {
                UniqueModuleList uniqueModuleList = semester.getModules();
                Iterator<Module> moduleIterator = uniqueModuleList.iterator();
                while (moduleIterator.hasNext()) {
                    Module module = moduleIterator.next();
                    UniqueTagList uniqueTagList = module.getTags();
                    DefaultTag completedTag = activeStudyPlan.getTags().getDefaultTag("Completed");
                    if (uniqueTagList.contains(completedTag)) {
                        continue;
                    }
                    uniqueTagList.addTag(completedTag);
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCurrentSemesterCommand // instanceof handles nulls
                && sem.equals(((SetCurrentSemesterCommand) other).sem));
    }
}
