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
 * Creates a new studyPlan.
 */
public class CreateStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "newplan";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Creating a new study plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new studyPlan."
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: " + COMMAND_WORD + " "
            + "newplan NOC halfyear";

    public static final String MESSAGE_SUCCESS = "New studyPlan added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDYPLAN = "This studyPlan already exists in the module planner";

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
                return new CommandResult(Title.MESSAGE_CONSTRAINTS);
            }
        }

        if (model.hasStudyPlan(studyPlanToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYPLAN);
        }

        model.addStudyPlan(studyPlanToAdd);
        studyPlanToAdd.setActivated(true);
        model.activateStudyPlan(studyPlanToAdd.getIndex());

        return new CommandResult(String.format(MESSAGE_SUCCESS, studyPlanName), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateStudyPlanCommand // instanceof handles nulls
                && studyPlanName.equals(((CreateStudyPlanCommand) other).studyPlanName));
    }
}
