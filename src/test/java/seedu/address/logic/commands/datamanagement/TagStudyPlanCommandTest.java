package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.PriorityTagType;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;

public class TagStudyPlanCommandTest {

    private Tag validTagOne;

    @BeforeEach
    public void setUp() {
        // construct priority tag
        validTagOne = new TagBuilder().buildPriorityHighTag();
    }

    @Test
    public void constructor_nullPriorityLevel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagStudyPlanCommand(null, 1));
    }

    @Test
    public void execute_tagNotInStudyPlan_addSuccessful() {
        // construct model containing study plan with no tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());

        // construct expected model containing study plan with one tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withStudyPlanTags(validTagOne).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to add a priority tag
        TagStudyPlanCommand tagStudyPlanCommand = new TagStudyPlanCommand("HIGH", 1);
        CommandResult expectedCommandResult = new CommandResult(String.format(TagStudyPlanCommand.MESSAGE_SUCCESS,
                validTagOne, studyPlan), true, false);
        assertCommandSuccess(tagStudyPlanCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_otherPriorityTagInStudyPlan_addSuccessful() {
        Tag validTagTwo = new TagBuilder().buildPriorityTag(PriorityTagType.LOW);

        // construct model containing study plan with one tag
        StudyPlan studyPlan = new StudyPlanBuilder().withStudyPlanTags(validTagOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());

        // construct expected model containing study plan with another tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withStudyPlanTags(validTagTwo).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to add a priority tag
        TagStudyPlanCommand tagStudyPlanCommand = new TagStudyPlanCommand("LOW", 1);
        CommandResult expectedCommandResult = new CommandResult(String.format(TagStudyPlanCommand.MESSAGE_SUCCESS,
                validTagTwo, studyPlan), true, false);
        assertCommandSuccess(tagStudyPlanCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_tagInStudyPlan_throwsCommandException() {
        // construct model containing study plan with the tag
        StudyPlan studyPlan = new StudyPlanBuilder().withStudyPlanTags(validTagOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());

        // construct command to add a priority tag
        TagStudyPlanCommand tagStudyPlanCommand = new TagStudyPlanCommand("HIGH", 1);
        assertThrows(CommandException.class, () -> tagStudyPlanCommand.execute(model),
                TagStudyPlanCommand.MESSAGE_TAG_ALREADY_EXISTS);
    }

    @Test
    public void execute_invalidStudyPlanIndex_throwsCommandException() {
        // construct model containing one study plan
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());

        // construct command to add a priority tag to a non-existent study plan
        TagStudyPlanCommand tagStudyPlanCommand = new TagStudyPlanCommand("HIGH", 3);
        assertThrows(CommandException.class, () -> tagStudyPlanCommand.execute(model),
                TagStudyPlanCommand.MESSAGE_NO_SUCH_STUDYPLAN);
    }

    @Test
    public void equals() {
        TagStudyPlanCommand tagStudyPlanCommand = new TagStudyPlanCommand("HIGH", 1);
        TagStudyPlanCommand tagOtherStudyPlanCommand = new TagStudyPlanCommand("HIGH", 2);
        TagStudyPlanCommand tagOtherTagToStudyPlanCommand = new TagStudyPlanCommand("MEDIUM", 1);

        // same object -> returns true
        assertTrue(tagStudyPlanCommand.equals(tagStudyPlanCommand));

        // same values -> returns true
        TagStudyPlanCommand tagStudyPlanCommandCopy = new TagStudyPlanCommand("HIGH", 1);
        assertTrue(tagStudyPlanCommand.equals(tagStudyPlanCommandCopy));

        // different types -> returns false
        assertFalse(tagStudyPlanCommand.equals(1));

        // null -> returns false
        assertFalse(tagStudyPlanCommand.equals(null));

        // different priority tag -> returns false
        assertFalse(tagStudyPlanCommand.equals(tagOtherTagToStudyPlanCommand));

        // different study plan -> returns false
        assertFalse(tagStudyPlanCommand.equals(tagOtherStudyPlanCommand));

    }

}
