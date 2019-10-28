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
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct expected model containing study plan with only one user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagTwo).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to delete one of the user tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(validTagNameOne);
        assertCommandSuccess(deleteTagCommand, model, String.format(DeleteTagCommand.MESSAGE_SUCCESS, validTagOne),
                expectedModel);
    }

    @Test
    public void execute_tagPresentInModules_deleteSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");

        // construct two modules with two user tags
        Module cs1231s = new ModuleBuilder().withTags(validTagOne, validTagTwo).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagOne, validTagTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules with only one user tag
        Module expectedCS1231S = new ModuleBuilder().withTags(validTagTwo).build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagTwo).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with only one user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to delete one of the user tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(validTagNameOne);
        assertCommandSuccess(deleteTagCommand, model, String.format(DeleteTagCommand.MESSAGE_SUCCESS, validTagOne),
                expectedModel);
    }

    @Test
    public void execute_tagNotPresentInStudyPlan_throwsCommandException() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to delete one of the user tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand("someOtherTag");
        assertThrows(CommandException.class, () -> deleteTagCommand.execute(model),
                DeleteTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void execute_deleteDefaultTag_throwsException() {
        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to delete one of the user tags
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

        // different user tag -> returns false
        assertFalse(deleteTestUserTagCommand.equals(deleteOtherUserTagCommand));
    }

}
