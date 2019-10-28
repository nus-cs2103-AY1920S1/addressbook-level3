package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
    public static final String MESSAGE_USAGE = COMMAND_WORD + " :Lists all study plans according to their priorities "
            + "Example: "
            + "listbypriority";

    public static final String MESSAGE_SUCCESS = "Here is a list of all the study plans you've created:\n";
    public static final String MESSAGE_NO_STUDYPLAN = "You don't have any study plan yet! Go create one now!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<StudyPlan> studyPlans = model.getFilteredStudyPlanList();

        if (studyPlans == null || studyPlans.size() == 0) {
            return new CommandResult(MESSAGE_NO_STUDYPLAN);
        }

        List<StudyPlan> studyPlansListCopy = new ArrayList<StudyPlan>();
        for (StudyPlan studyPlan: studyPlans) {
            studyPlansListCopy.add(studyPlan);
        }

        studyPlansListCopy.sort(new Comparator<StudyPlan>() {
            public int compare(StudyPlan studyPlan1, StudyPlan studyPlan2) {
                if (studyPlan1.equals(studyPlan2)) {
                    return 0;
                } else {
                    if (studyPlan1.getPriorityTag() == null) {
                        if (studyPlan2.getPriorityTag() == null) {
                            return studyPlan1.getTitle().toString().compareTo(studyPlan2.getTitle().toString());
                        } else {
                            return -1;
                        }
                    } else {
                        if (studyPlan2.getPriorityTag() == null) {
                            return 1;
                        } else {
                            return studyPlan1.getPriorityTag().compareTo(studyPlan2.getPriorityTag());
                        }
                    }
                }
            }
        });

        StringBuilder toReturn = new StringBuilder(MESSAGE_SUCCESS);
        Iterator<StudyPlan> studyPlanIterator = studyPlans.iterator();
        while (studyPlanIterator.hasNext()) {
            StudyPlan studyPlan = studyPlanIterator.next();
            toReturn.append(studyPlan.toString() + "\n");
        }
        return new CommandResult(toReturn.toString());
    }

}
