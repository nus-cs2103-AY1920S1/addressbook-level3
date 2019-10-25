package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;


public class DeleteTagCommandTest {

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteTagCommand(null));
    }

    @Test
    public void execute_tagPresentInStudyPlan_deleteSuccessful() {
        // construct tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct model containing study plan with two tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct expected model containing study plan with only one tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameTwo).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.addToHistory();

        // construct command to delete one of the tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(validTagNameOne);
        assertCommandSuccess(deleteTagCommand, model, String.format(DeleteTagCommand.MESSAGE_SUCCESS, validTagOne),
                expectedModel);
    }

    @Test
    public void execute_tagPresentInModules_deleteSuccessful() {
        // construct tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct two modules with two tags
        Module cs1231 = new ModuleBuilder().withTags(validTagNameOne, validTagNameTwo).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameOne, validTagNameTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("moduleOne", cs1231);
        moduleHashMap.put("moduleTwo", cs2100);


        // construct model containing study plan with two tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules with only one tags
        Module expectedCS1231 = new ModuleBuilder().withTags(validTagNameTwo).build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameTwo).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231", expectedCS1231);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with only one tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.addToHistory();

        // construct command to delete one of the tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(validTagNameOne);
        assertCommandSuccess(deleteTagCommand, model, String.format(DeleteTagCommand.MESSAGE_SUCCESS, validTagOne),
                expectedModel);
    }

    @Test
    public void execute_tagNotPresentInStudyPlan_throwsException() throws Exception {
        // construct tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct model containing study plan with two tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to delete one of the tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand("someOtherTag");
        assertThrows(CommandException.class, () -> deleteTagCommand.execute(model),
                DeleteTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void execute_deleteDefaultTag_throwsException() throws Exception {
        // construct model containing study plan with two tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to delete one of the tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand("Core");
        assertThrows(CommandException.class, () -> deleteTagCommand.execute(model),
                DeleteTagCommand.MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
    }

    @Test
    public void equals() {
        DeleteTagCommand deleteTestUserTagCommand = new DeleteTagCommand("testUserTag");
        DeleteTagCommand deleteOtherUserTagCommand = new DeleteTagCommand("otherUserTag");

        // same object -> returns true
        assertTrue(deleteTestUserTagCommand.equals(deleteTestUserTagCommand));

        // same values -> returns true
        DeleteTagCommand deleteTestUserTagCommandCopy = new DeleteTagCommand("testUserTag");
        assertTrue(deleteTestUserTagCommand.equals(deleteTestUserTagCommandCopy));

        // different types -> returns false
        assertFalse(deleteTestUserTagCommand.equals(1));

        // null -> returns false
        assertFalse(deleteTestUserTagCommand.equals(null));

        // different studyPlan -> returns false
        assertFalse(deleteTestUserTagCommand.equals(deleteOtherUserTagCommand));
    }

}
