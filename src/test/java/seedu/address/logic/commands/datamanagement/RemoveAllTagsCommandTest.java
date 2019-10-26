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

public class RemoveAllTagsCommandTest {

    @Test
    public void execute_userTagsPresentInStudyPlanAndModules_allTagsRemovedSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct two modules with two user tags
        Module cs1231 = new ModuleBuilder().withTags(validTagNameOne, validTagNameTwo).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameOne, validTagNameTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap)
                .withTags(validTagNameOne, validTagNameTwo).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules without any user tags
        Module expectedCS1231 = new ModuleBuilder().build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231", expectedCS1231);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with two user tags and expected modules
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.addToHistory();

        // construct command to remove all user tags
        RemoveAllTagsCommand removeAllTagsCommand = new RemoveAllTagsCommand();
        assertCommandSuccess(removeAllTagsCommand, model, RemoveAllTagsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noUserTagsPresentInStudyPlanAndModules_throwsException() {
        // construct two modules with no user tags
        Module cs1231 = new ModuleBuilder().build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove all tags
        RemoveAllTagsCommand removeAllTagsCommand = new RemoveAllTagsCommand();
        assertThrows(CommandException.class, () -> removeAllTagsCommand.execute(model),
                RemoveAllTagsCommand.MESSAGE_NO_TAGS_TO_REMOVE);
    }

    @Test
    public void equals() {
        RemoveAllTagsCommand removeAllTagsCommand = new RemoveAllTagsCommand();

        // same object -> returns true
        assertTrue(removeAllTagsCommand.equals(removeAllTagsCommand));

        // same type -> returns true
        RemoveAllTagsCommand removeAllTagsCommandCopy = new RemoveAllTagsCommand();
        assertTrue(removeAllTagsCommand.equals(removeAllTagsCommandCopy));

        // different types -> returns false
        assertFalse(removeAllTagsCommand.equals(1));

        // null -> returns false
        assertFalse(removeAllTagsCommand.equals(null));

    }

}
