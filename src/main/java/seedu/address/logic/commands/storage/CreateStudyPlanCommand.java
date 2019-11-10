package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModulesInfo;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.studyplan.exceptions.InvalidTitleException;

/**
 * Creates a new study plan.
 */
public class CreateStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "newplan";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Creating a new study plan with a new unique ID";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new study plan with a new unique ID."
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: " + COMMAND_WORD + " "
            + "newplan NOC halfyear";

    public static final String MESSAGE_SUCCESS = "New study plan added: %1$s [unique ID: %2$d]";
    public static final String MESSAGE_DUPLICATE_STUDY_PLAN = "This study plan already exists in the module planner";

    private final String studyPlanName;

    /**
     * Creates an CreateStudyPlanCommand to add the specified {@code StudyPlan}
     */
    public CreateStudyPlanCommand(String studyPlanName) {
        this.studyPlanName = studyPlanName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModulesInfo modulesInfo = model.getModulesInfo();
        StudyPlan studyPlanToAdd;
        if (studyPlanName == null || studyPlanName.isEmpty()) {
            // study plan with no title
            studyPlanToAdd = new StudyPlan(modulesInfo, model.getModulePlanner().getCurrentSemester());
        } else {
            // study plan with a title
            try {
                SemesterName currentSemester = model.getModulePlanner().getCurrentSemester();
                studyPlanToAdd = new StudyPlan(new Title(studyPlanName), modulesInfo, currentSemester);
            } catch (InvalidTitleException e) {
                throw new CommandException(Title.MESSAGE_CONSTRAINTS);
            }
        }

        if (model.hasStudyPlan(studyPlanToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDY_PLAN);
        }

        model.addStudyPlan(studyPlanToAdd);
        studyPlanToAdd.setActivated(true);
        model.activateStudyPlan(studyPlanToAdd.getIndex());

        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, studyPlanName, studyPlanToAdd.getIndex()),
                true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateStudyPlanCommand // instanceof handles nulls
                && studyPlanName.equals(((CreateStudyPlanCommand) other).studyPlanName));
    }
}
