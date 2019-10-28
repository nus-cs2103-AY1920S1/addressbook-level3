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

public class TagModuleCommandTest {

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        String validModuleCode = TypicalModule.CS1101S.getModuleCode().toString();
        assertThrows(NullPointerException.class, () -> new TagModuleCommand(null, validModuleCode));
    }

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        String validTagName = new TagBuilder().buildTestUserTag().getTagName();
        assertThrows(NullPointerException.class, () -> new TagModuleCommand(validTagName, null));
    }

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagModuleCommand(null, null));
    }

    @Test
    public void execute_tagNotPresentInStudyPlan_newTagCreatedAndAddSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct two modules with no user tags
        Module cs1231s = new ModuleBuilder().build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules, one with a user tag and one without
        Module expectedCS1231S = new ModuleBuilder().build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagOne).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with one user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to add a user tag
        TagModuleCommand tagModuleCommand = new TagModuleCommand(validTagNameOne, "CS2100");
        assertCommandSuccess(tagModuleCommand, model, String.format(TagModuleCommand.MESSAGE_SUCCESS_TAG_CREATED,
                validTagOne, cs2100.getModuleCode().toString()), expectedModel);
    }

    @Test
    public void execute_tagPresentInStudyPlan_addSuccessful() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct two modules with no user tags
        Module cs1231s = new ModuleBuilder().build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with a user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules, one with a tag and one without
        Module expectedCS1231S = new ModuleBuilder().build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagOne).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with one tag and expected modules
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to add a tag
        TagModuleCommand tagModuleCommand = new TagModuleCommand(validTagNameOne, "CS2100");
        assertCommandSuccess(tagModuleCommand, model, String.format(TagModuleCommand.MESSAGE_SUCCESS,
                validTagOne, cs2100.getModuleCode().toString()), expectedModel);
    }

    @Test
    public void execute_tagPresentInModule_throwsCommandException() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct a module with the user tag
        Module cs1231s = new ModuleBuilder().withTags(validTagOne).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with the user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to add a tag
        TagModuleCommand tagModuleCommand = new TagModuleCommand(validTagNameOne, "CS1231S");
        assertThrows(CommandException.class, () -> tagModuleCommand.execute(model),
                String.format(TagModuleCommand.MESSAGE_EXISTING_TAG, validTagNameOne,
                        cs1231s.getModuleCode().toString()));
    }

    @Test
    public void execute_addDefaultTag_throwsCommandException() {
        String validDefaultTagName = new TagBuilder().buildDefaultCoreTag().getTagName();
        String validModuleCode = TypicalModule.CS1101S.getModuleCode().toString();

        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to add a tag
        TagModuleCommand tagModuleCommand = new TagModuleCommand(validDefaultTagName, validModuleCode);
        assertThrows(CommandException.class, () -> tagModuleCommand.execute(model),
                TagModuleCommand.MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
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
        TagModuleCommand tagModuleCommand =
                new TagModuleCommand(validTagOne.getTagName(), "CS3333");

        assertThrows(CommandException.class, () -> tagModuleCommand.execute(model),
                String.format(TagModuleCommand.MESSAGE_MODULE_DOES_NOT_EXIST, "CS3333"));
    }

    @Test
    public void equals() {
        TagModuleCommand tagUserTagToModuleCommand =
                new TagModuleCommand("testUserTag", "CS1231S");
        TagModuleCommand tagOtherUserTagToModuleCommand =
                new TagModuleCommand("otherUserTag", "CS1231S");
        TagModuleCommand tagUserTagToOtherModuleCommand =
                new TagModuleCommand("testUserTag", "CS2100");

        // same object -> returns true
        assertTrue(tagUserTagToModuleCommand.equals(tagUserTagToModuleCommand));

        // same values -> returns true
        TagModuleCommand tagUserTagToModuleCommandCopy =
                new TagModuleCommand("testUserTag", "CS1231S");
        assertTrue(tagUserTagToModuleCommand.equals(tagUserTagToModuleCommandCopy));

        // different types -> returns false
        assertFalse(tagUserTagToModuleCommand.equals(1));

        // null -> returns false
        assertFalse(tagUserTagToModuleCommand.equals(null));

        // different user tag -> returns false
        assertFalse(tagUserTagToModuleCommand.equals(tagOtherUserTagToModuleCommand));

        // different module code -> returns false
        assertFalse(tagUserTagToModuleCommand.equals(tagUserTagToOtherModuleCommand));
    }
}
