package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

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
        // construct tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct two modules with no tags
        Module cs1231 = new ModuleBuilder().build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with no tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two modules, one with a tag and one without
        Module expectedCS1231 = new ModuleBuilder().build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameOne).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231", expectedCS1231);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with one tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameOne)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.addToHistory();

        // construct command to add a tag
        TagModuleCommand tagModuleCommand = new TagModuleCommand(validTagNameOne, "CS2100");
        assertCommandSuccess(tagModuleCommand, model, String.format(TagModuleCommand.MESSAGE_SUCCESS_TAG_ADDED,
                validTagOne, cs2100.getModuleCode().toString()), expectedModel);
    }

}
