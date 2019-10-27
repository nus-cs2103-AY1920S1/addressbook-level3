package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.ui.ResultViewType;

public class ViewAllTagsCommandTest {

    @Test
    public void execute_userTagsPresentInStudyPlan_allTagsShownSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherTag");

        // construct model containing study plan with user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to view all tags
        ViewAllTagsCommand viewAllTagsCommand = new ViewAllTagsCommand();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();
        expectedList.initDefaultTags();;
        expectedList.addTag(validTagOne);
        expectedList.addTag(validTagTwo);

        CommandResult expectedCommandResult = new CommandResult(ViewAllTagsCommand.MESSAGE_SUCCESS,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());
        assertCommandSuccess(viewAllTagsCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_noUserTagsPresentInStudyPlan_allTagsShownSuccessful() {
        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to view all tags
        ViewAllTagsCommand viewAllTagsCommand = new ViewAllTagsCommand();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();
        expectedList.initDefaultTags();

        CommandResult expectedCommandResult = new CommandResult(ViewAllTagsCommand.MESSAGE_SUCCESS,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());
        assertCommandSuccess(viewAllTagsCommand, model, expectedCommandResult, model);

        assertCommandSuccess(viewAllTagsCommand, model, expectedCommandResult, model);
    }

    @Test
    public void equals() {
        ViewAllTagsCommand viewAllTagsCommand = new ViewAllTagsCommand();

        // same object -> returns true
        assertTrue(viewAllTagsCommand.equals(viewAllTagsCommand));

        // same type -> returns true
        ViewAllTagsCommand viewAllTagsCommandCopy = new ViewAllTagsCommand();
        assertTrue(viewAllTagsCommand.equals(viewAllTagsCommandCopy));

        // different types -> returns false
        assertFalse(viewAllTagsCommand.equals(1));

        // null -> returns false
        assertFalse(viewAllTagsCommand.equals(null));

    }

}
