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

public class RenameTagCommandTest {

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewTaggedCommand("exchange", null));
        assertThrows(NullPointerException.class, () -> new ViewTaggedCommand(null, "exchange"));
    }

    @Test
    public void execute_tagPresentInStudyPlan_renameSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct model containing study plan with one user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct expected model containing study plan with renamed user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameTwo).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.addToHistory();

        // construct command to rename one of the user tags
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, validTagNameTwo);
        assertCommandSuccess(renameTagCommand, model, String.format(RenameTagCommand.MESSAGE_SUCCESS, validTagNameOne,
                validTagTwo), expectedModel);
    }

    @Test
    public void execute_tagPresentInStudyPlanAndModules_renameSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();
        Tag validTagThree = new TagBuilder().buildUserTag("anotherUserTag");
        String validTagNameThree = validTagThree.getTagName();

        // construct modules with user tags
        Module cs1231 = new ModuleBuilder().withTags(validTagNameOne).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameOne, validTagNameTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with one user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules with renamed user tag
        Module expectedCS1231 = new ModuleBuilder().withTags(validTagNameThree).build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100")
                .withTags(validTagNameThree, validTagNameTwo).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231", expectedCS1231);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with renamed user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameThree, validTagNameTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.addToHistory();

        // construct command to rename one of the user tags
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, validTagNameThree);
        assertCommandSuccess(renameTagCommand, model, String.format(RenameTagCommand.MESSAGE_SUCCESS, validTagNameOne,
                validTagThree), expectedModel);
    }

    @Test
    public void execute_renameDefaultTag_throwsCommandException() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        String defaultTagName = new TagBuilder().buildDefaultCoreTag().getTagName();

        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to rename one of the default tags
        RenameTagCommand renameTagCommand = new RenameTagCommand(defaultTagName, "someOtherName");
        assertThrows(CommandException.class, () -> renameTagCommand.execute(model),
                RenameTagCommand.MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
    }

    @Test
    public void execute_renameToDefaultTagName_throwsCommandException() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        String defaultTagName = new TagBuilder().buildDefaultCoreTag().getTagName();

        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to rename one of the user tags to a default tag name
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, defaultTagName);
        assertThrows(CommandException.class, () -> renameTagCommand.execute(model),
                RenameTagCommand.MESSAGE_INVALID_TAG_NAME);
    }

    @Test
    public void execute_tagNotPresentInStudyPlan_throwsCommandException() {
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to rename validTagOne to a new name
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, "someOtherName");
        assertThrows(CommandException.class, () -> renameTagCommand.execute(model),
                RenameTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void equals() {
        RenameTagCommand renameUserTagCommand = new RenameTagCommand("oldName", "newName");
        RenameTagCommand renameOtherUserTagCommand = new RenameTagCommand("anotherOldName", "newName");
        RenameTagCommand renameUserTagToOtherNameCommand = new RenameTagCommand("oldName",
                "anotherNewName");

        // same object -> returns true
        assertTrue(renameUserTagCommand.equals(renameUserTagCommand));

        // same values -> returns true
        RenameTagCommand renameUserTagCommandCopy = new RenameTagCommand("oldName",
                "newName");
        assertTrue(renameUserTagCommand.equals(renameUserTagCommandCopy));

        // different types -> returns false
        assertFalse(renameUserTagCommand.equals(1));

        // null -> returns false
        assertFalse(renameUserTagCommand.equals(null));

        // different user tag to rename -> returns false
        assertFalse(renameUserTagCommand.equals(renameOtherUserTagCommand));

        // different new name to rename user tag to -> returns false
        assertFalse(renameUserTagCommand.equals(renameUserTagToOtherNameCommand));
    }

}
