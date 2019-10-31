package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a command for the user to view all study plans.
 */
public class ListAllStudyPlansCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Listing all the study plans";
    public static final String MESSAGE_SUCCESS = "Here is a list of all the study plans you've created:\n";
    public static final String MESSAGE_NO_STUDYPLAN = "You don't have any study plan yet! Go create one now!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all study plans that have been created.\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<StudyPlan> studyPlans = model.getFilteredStudyPlanList();

        if (studyPlans == null || studyPlans.size() == 0) {
            return new CommandResult(MESSAGE_NO_STUDYPLAN);
        }

        StringBuilder toReturn = new StringBuilder(MESSAGE_SUCCESS);
        for (StudyPlan studyPlan : studyPlans) {
            toReturn.append(studyPlan.toString()).append("\n");
        }
        return new CommandResult(toReturn.toString());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ListAllStudyPlansCommand;
    }
}
