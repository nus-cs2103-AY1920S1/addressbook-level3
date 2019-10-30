package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.PriorityTagType;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;

public class SortStudyPlansByPriorityTagCommandTest {

    @Test
    public void execute_studyPlansPresent_sortSuccessful() {
        // construct study plans
        StudyPlan studyPlanOne = new StudyPlanBuilder().withIndex(1).build();
        StudyPlan studyPlanTwo = new StudyPlanBuilder().withStudyPlanTags(
                new TagBuilder().buildPriorityTag(PriorityTagType.LOW)).build();
        StudyPlan studyPlanThree = new StudyPlanBuilder().withStudyPlanTags(
                new TagBuilder().buildPriorityTag(PriorityTagType.MEDIUM)).build();
        StudyPlan studyPlanFour = new StudyPlanBuilder().withStudyPlanTags(new TagBuilder().buildPriorityHighTag())
                .build();
        StudyPlan studyPlanFive = new StudyPlanBuilder().withIndex(2).build();

        // construct model with study plans
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlans(studyPlanOne, studyPlanTwo,
                studyPlanThree, studyPlanFour, studyPlanFive).build(), new UserPrefs(),
                        TypicalModulesInfo.getTypicalModulesInfo());

        // construct expected string
        StringBuilder toReturn = new StringBuilder(SortStudyPlansByPriorityTagCommand.MESSAGE_SUCCESS);
        toReturn.append(studyPlanFour.toString() + " [PRIORITY.HIGH]\n");
        toReturn.append(studyPlanThree.toString() + " [PRIORITY.MEDIUM]\n");
        toReturn.append(studyPlanTwo.toString() + " [PRIORITY.LOW]\n");
        toReturn.append(studyPlanOne.toString() + "\n");
        toReturn.append(studyPlanFive.toString() + "\n");

        SortStudyPlansByPriorityTagCommand sortStudyPlansByPriorityTagCommand =
                new SortStudyPlansByPriorityTagCommand();
        assertCommandSuccess(sortStudyPlansByPriorityTagCommand, model, toReturn.toString(), model);
    }

    @Test
    public void execute_noStudyPlanPresent_sortSuccess() {
        // construct model with no study plans
        Model model = new ModelManager(new ModulePlannerBuilder().build(), new UserPrefs(),
                TypicalModulesInfo.getTypicalModulesInfo());

        SortStudyPlansByPriorityTagCommand sortStudyPlansByPriorityTagCommand =
                new SortStudyPlansByPriorityTagCommand();
        assertCommandSuccess(sortStudyPlansByPriorityTagCommand, model,
                SortStudyPlansByPriorityTagCommand.MESSAGE_NO_STUDYPLAN, model);
    }

    @Test
    public void equals() {
        SortStudyPlansByPriorityTagCommand sortStudyPlansByPriorityTagCommand =
                new SortStudyPlansByPriorityTagCommand();

        // same object -> returns true
        assertTrue(sortStudyPlansByPriorityTagCommand.equals(sortStudyPlansByPriorityTagCommand));

        // same type -> returns true
        SortStudyPlansByPriorityTagCommand sortStudyPlansByPriorityTagCommandCopy =
                new SortStudyPlansByPriorityTagCommand();
        assertTrue(sortStudyPlansByPriorityTagCommand.equals(sortStudyPlansByPriorityTagCommandCopy));

        // different types -> returns false
        assertFalse(sortStudyPlansByPriorityTagCommand.equals(1));

        // null -> returns false
        assertFalse(sortStudyPlansByPriorityTagCommand.equals(null));

    }

}
