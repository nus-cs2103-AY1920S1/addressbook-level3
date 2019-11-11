package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Sorts study plans in the order of their priority.
 */
public class SortStudyPlansByPriorityTagCommand extends Command {

    public static final String COMMAND_WORD = "listbypriority";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Lists study plans according to priority tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all study plans according to their priorities "
            + "Example: "
            + "listbypriority";

    public static final String MESSAGE_SUCCESS = "Here is a list of all the study plans you've created according "
            + "to their priorities (optional):\n";
    public static final String MESSAGE_NO_STUDYPLAN = "You don't have any study plan yet! Go create one now!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<StudyPlan> studyPlans = model.getFilteredStudyPlanList();

        if (studyPlans == null || studyPlans.size() == 0) {
            return new CommandResult(MESSAGE_NO_STUDYPLAN);
        }

        List<StudyPlan> sortedList = studyPlans.stream().sorted((studyPlan1, studyPlan2) -> {
            if (studyPlan1.getPriorityTag() == null) {
                if (studyPlan2.getPriorityTag() == null) {
                    return studyPlan1.getIndex() - studyPlan2.getIndex();
                } else {
                    return 1;
                }
            } else {
                if (studyPlan2.getPriorityTag() == null) {
                    return -1;
                } else {
                    return studyPlan1.getPriorityTag().compareTo(studyPlan2.getPriorityTag());
                }
            }
        })
                .collect(Collectors.toList());

        StringBuilder toReturn = new StringBuilder(MESSAGE_SUCCESS);
        Iterator<StudyPlan> studyPlanIterator = sortedList.iterator();
        while (studyPlanIterator.hasNext()) {
            StudyPlan studyPlan = studyPlanIterator.next();
            toReturn.append(studyPlan.toString());
            if (studyPlan.containsPriorityTag()) {
                toReturn.append(" [" + studyPlan.getPriorityTag().getTagName() + "]\n");
            } else {
                toReturn.append("\n");
            }
        }
        return new CommandResult(toReturn.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortStudyPlansByPriorityTagCommand); // instanceof handles nulls and type check
    }

}
