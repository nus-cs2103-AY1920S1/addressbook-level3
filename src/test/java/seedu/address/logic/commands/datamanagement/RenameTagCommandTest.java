package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
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

    private Tag validTagOne;
    private String validTagNameOne;
    private Tag validTagTwo;
    private String validTagNameTwo;

    @BeforeEach
    public void setUp() {
        // construct user tags
        validTagOne = new TagBuilder().buildTestUserTag();
        validTagNameOne = validTagOne.getTagName();
        validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        validTagNameTwo = validTagTwo.getTagName();
    }

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewTaggedCommand(validTagNameOne, null));
        assertThrows(NullPointerException.class, () -> new ViewTaggedCommand(null, validTagNameOne));
    }

    @Test
    public void execute_tagPresentInStudyPlan_renameSuccessful() {
        // construct model containing study plan with one user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct expected model containing study plan with renamed user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagTwo).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to rename one of the user tags
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, validTagNameTwo);
        assertCommandSuccess(renameTagCommand, model, String.format(RenameTagCommand.MESSAGE_SUCCESS, validTagNameOne,
                validTagTwo.getTagName()), expectedModel);
    }

    @Test
    public void execute_tagPresentInStudyPlanAndModules_renameSuccessful() {
        // construct another user tag
        Tag validTagThree = new TagBuilder().buildUserTag("anotherUserTag");
        String validTagNameThree = validTagThree.getTagName();

        // construct modules with user tags
        Module cs1231s = new ModuleBuilder().withTags(validTagOne).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagOne, validTagTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with one user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules with renamed user tag
        Module expectedCS1231S = new ModuleBuilder().withTags(validTagThree).build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100")
                .withTags(validTagThree, validTagTwo).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with renamed user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagThree, validTagTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to rename one of the user tags
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, validTagNameThree);
        assertCommandSuccess(renameTagCommand, model, String.format(RenameTagCommand.MESSAGE_SUCCESS, validTagNameOne,
                validTagThree.getTagName()), expectedModel);
    }

    @Test
    public void execute_newTagPresentInStudyPlanAndModules_renameSuccessful() {
        // construct modules with user tags
        Module cs1231s = new ModuleBuilder().withTags(validTagOne).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagOne, validTagTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with one user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct two expected modules with renamed user tag
        Module expectedCS1231S = new ModuleBuilder().withTags(validTagTwo).build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100")
                .withTags(validTagTwo).build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);
        expectedModuleHashMap.put("CS2100", expectedCS2100);

        // construct expected model containing study plan with renamed user tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagTwo)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to rename one of the user tags
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, validTagNameTwo);
        assertCommandSuccess(renameTagCommand, model, String.format(RenameTagCommand.MESSAGE_SUCCESS, validTagNameOne,
                validTagNameTwo), expectedModel);
    }

    @Test
    public void execute_renameDefaultTag_throwsCommandException() {
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
    public void execute_renameToSameName_throwsCommandException() {
        // construct model containing study plan with one tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to rename tag to same name
        RenameTagCommand renameTagCommand = new RenameTagCommand(validTagNameOne, validTagNameOne);
        assertThrows(CommandException.class, () -> renameTagCommand.execute(model),
                String.format(RenameTagCommand.MESSAGE_SAME_TAG_NAME, validTagNameOne, validTagNameOne));
    }

    @Test
    public void execute_renameToDefaultTagName_throwsCommandException() {
        String defaultTagName = new TagBuilder().buildDefaultCoreTag().getTagName();

        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne).build();
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
