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
import seedu.address.testutil.TypicalModule;
import seedu.address.testutil.TypicalModulesInfo;

public class RemoveTagFromModuleCommandTest {

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        String validModuleCode = TypicalModule.CS1101S.getModuleCode().toString();
        assertThrows(NullPointerException.class, () -> new TagModuleCommand(validModuleCode, null));
    }

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        String validTagName = new TagBuilder().buildTestUserTag().getTagName();
        assertThrows(NullPointerException.class, () -> new TagModuleCommand(null, validTagName));
    }

    @Test
    public void execute_tagPresentInModule_deleteSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");

        // construct a module
        Module cs1231s = new ModuleBuilder().withTags(validTagOne, validTagTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct the expected module with only one user tag
        Module expectedCS1231S = new ModuleBuilder().withTags(validTagTwo).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);

        // construct expected model containing study plan with two user tags and the expected module
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to remove the user tag from the module
        RemoveTagFromModuleCommand removeTagFromModuleCommand = new RemoveTagFromModuleCommand("CS1231S",
                validTagNameOne);
        assertCommandSuccess(removeTagFromModuleCommand, model,
                String.format(RemoveTagFromModuleCommand.MESSAGE_SUCCESS, validTagOne,
                        cs1231s.getModuleCode().toString()), expectedModel);
    }

    @Test
    public void execute_tagNotPresentInStudyPlan_throwsCommandException() {
        String validTagName = new TagBuilder().buildTestUserTag().getTagName();

        // construct a module with no user tags
        Module cs1231s = new ModuleBuilder().build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing a study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove the user tag from the module
        RemoveTagFromModuleCommand removeTagFromModuleCommand = new RemoveTagFromModuleCommand("CS1231S",
                validTagName);
        assertThrows(CommandException.class, () -> removeTagFromModuleCommand.execute(model),
                String.format(RemoveTagFromModuleCommand.MESSAGE_TAG_NOT_FOUND, cs1231s.getModuleCode().toString(),
                        validTagName));
    }

    @Test
    public void execute_tagNotPresentInModule_throwsCommandException() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct a module with no user tags
        Module cs1231s = new ModuleBuilder().build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with one user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove the user tag from the module
        RemoveTagFromModuleCommand removeTagFromModuleCommand = new RemoveTagFromModuleCommand("CS1231S",
                validTagNameOne);
        assertThrows(CommandException.class, () -> removeTagFromModuleCommand.execute(model),
                String.format(RemoveTagFromModuleCommand.MESSAGE_TAG_NOT_FOUND, cs1231s.getModuleCode().toString(),
                        validTagNameOne));
    }

    @Test
    public void execute_removeDefaultTag_throwsCommandException() {
        String validDefaultTagName = new TagBuilder().buildDefaultCoreTag().getTagName();

        // construct a module with no user tags
        Module cs1231s = new ModuleBuilder().build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove the tag from the module
        RemoveTagFromModuleCommand removeTagFromModuleCommand = new RemoveTagFromModuleCommand("CS1231S",
                validDefaultTagName);
        assertThrows(CommandException.class, () -> removeTagFromModuleCommand.execute(model),
                RemoveTagFromModuleCommand.MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
    }

    @Test
    public void execute_moduleDoesNotExist_throwsCommandException() {
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to find non-existent module
        RemoveTagFromModuleCommand removeTagFromModuleCommand =
                new RemoveTagFromModuleCommand("CS3333", validTagNameOne);

        assertThrows(CommandException.class, () -> removeTagFromModuleCommand.execute(model),
                String.format(RemoveTagFromModuleCommand.MESSAGE_MODULE_DOES_NOT_EXIST, "CS3333"));
    }

    @Test
    public void equals() {
        RemoveTagFromModuleCommand removeUserTagFromModuleCommand =
                new RemoveTagFromModuleCommand("CS1231S", "testUserTag");
        RemoveTagFromModuleCommand removeOtherUserTagFromModuleCommand =
                new RemoveTagFromModuleCommand("CS1231S", "otherUserTag");
        RemoveTagFromModuleCommand removeUserTagFromOtherModuleCommand =
                new RemoveTagFromModuleCommand("CS2100", "testUserTag");

        // same object -> returns true
        assertTrue(removeUserTagFromModuleCommand.equals(removeUserTagFromModuleCommand));

        // same values -> returns true
        RemoveTagFromModuleCommand removeUserTagFromModuleCommandCopy =
                new RemoveTagFromModuleCommand("CS1231S", "testUserTag");
        assertTrue(removeUserTagFromModuleCommand.equals(removeUserTagFromModuleCommandCopy));

        // different types -> returns false
        assertFalse(removeUserTagFromModuleCommand.equals(1));

        // null -> returns false
        assertFalse(removeUserTagFromModuleCommand.equals(null));

        // different user tag -> returns false
        assertFalse(removeUserTagFromModuleCommand.equals(removeOtherUserTagFromModuleCommand));

        // different module code -> returns false
        assertFalse(removeUserTagFromModuleCommand.equals(removeUserTagFromOtherModuleCommand));
    }

}
