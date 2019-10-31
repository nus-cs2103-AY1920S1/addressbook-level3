package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModulesInfo;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.util.DefaultStudyPlanUtil;

/**
 * Creates a new recommended study plan based on
 * https://www.comp.nus.edu.sg/images/resources/content/undergraduates/study_planner-CS2019.pdf.
 */
public class DefaultStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "default";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new recommended sample study plan.";
    public static final String MESSAGE_SUCCESS = "New sample study plan added!";
    public static final String MESSAGE_DUPLICATE_STUDYPLAN = "This study plan already exists in the module planner";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModulesInfo modulesInfo = model.getModulesInfo();
        StudyPlan defaultStudyPlan = DefaultStudyPlanUtil.getDefaultStudyPlan(modulesInfo);

        if (model.hasStudyPlan(defaultStudyPlan)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYPLAN);
        }

        model.addStudyPlan(defaultStudyPlan);
        defaultStudyPlan.setActivated(true);
        model.activateStudyPlan(defaultStudyPlan.getIndex());

        model.addToHistory();

        return new CommandResult(MESSAGE_SUCCESS, true, false);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DefaultStudyPlanCommand; // instanceof handles nulls
    }
}
