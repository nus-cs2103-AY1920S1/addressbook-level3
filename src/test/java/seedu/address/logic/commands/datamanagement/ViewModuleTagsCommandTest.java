package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.ui.ResultViewType;

public class ViewModuleTagsCommandTest {

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewModuleTagsCommand(null));
    }

    @Test
    public void execute_userTagsPresentInModule_allModuleTagsShownSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");

        // construct module with one user tag
        Module cs1231s = new ModuleBuilder().withModuleCode("CS1231S").withTags(validTagOne).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne, validTagTwo)
                .withModules(moduleHashMap).build();

        // assign default tags to the module
        UniqueTagList moduleDefaultTags = studyPlan.assignDefaultTags(TypicalModulesInfo.CS1231S);
        for (Tag defaultTag: moduleDefaultTags) {
            cs1231s.addTag(defaultTag);
        }

        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();
        expectedList.addTag(validTagOne);
        expectedList.addTag(new TagBuilder().buildDefaultTag(DefaultTagType.SU));
        expectedList.addTag(new TagBuilder().buildDefaultCoreTag());

        CommandResult expectedCommandResult = new CommandResult(ViewModuleTagsCommand.MESSAGE_SUCCESS,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());

        // construct command to show all tags for the module
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS1231S");
        assertCommandSuccess(viewModuleTagsCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_noUserTagsPresentInModule_allModuleTagsShownSuccessful() {
        Tag validTagOne = new TagBuilder().buildTestUserTag();

        // construct module with no user tags
        Module cs1231s = new ModuleBuilder().withModuleCode("CS1231S").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(moduleHashMap).build();
        // assign default tags to the module
        cs1231s.getTags().setTags(studyPlan.assignDefaultTags(TypicalModulesInfo.CS1231S));

        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();
        expectedList.addTag(new TagBuilder().buildDefaultTag(DefaultTagType.SU));
        expectedList.addTag(new TagBuilder().buildDefaultCoreTag());

        CommandResult expectedCommandResult = new CommandResult(ViewModuleTagsCommand.MESSAGE_SUCCESS,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());

        // construct command to show all tags for the module
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS1231S");
        assertCommandSuccess(viewModuleTagsCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_noTagsPresentInModule_throwsCommandException() {
        // construct module with no user tags
        Module cs2101 = new ModuleBuilder().withModuleCode("CS2101").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS2101", cs2101);

        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();

        CommandResult expectedCommandResult = new CommandResult(ViewModuleTagsCommand.MESSAGE_NO_TAGS_FOUND,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());

        // construct command to show all tags for the module
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS2101");
        assertThrows(CommandException.class, () -> viewModuleTagsCommand.execute(model),
                ViewModuleTagsCommand.MESSAGE_NO_TAGS_FOUND);
    }

    @Test
    public void execute_moduleDoesNotExist_throwsCommandException() {
        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to find non-existent module
        ViewModuleTagsCommand viewModuleTagsCommand =
                new ViewModuleTagsCommand("CS3333");

        assertThrows(CommandException.class, () -> viewModuleTagsCommand.execute(model),
                String.format(ViewModuleTagsCommand.MESSAGE_MODULE_DOES_NOT_EXIST, "CS3333"));
    }

    @Test
    public void equals() {
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS1231S");
        ViewModuleTagsCommand viewOtherModuleTagsCommand = new ViewModuleTagsCommand("CS3230");

        // same object -> returns true
        assertTrue(viewModuleTagsCommand.equals(viewModuleTagsCommand));

        // same values -> returns true
        ViewModuleTagsCommand viewModuleTagsCommandCopy = new ViewModuleTagsCommand("CS1231S");
        assertTrue(viewModuleTagsCommand.equals(viewModuleTagsCommandCopy));

        // different types -> returns false
        assertFalse(viewModuleTagsCommand.equals(1));

        // null -> returns false
        assertFalse(viewModuleTagsCommand.equals(null));

        // different module code -> returns false
        assertFalse(viewModuleTagsCommand.equals(viewOtherModuleTagsCommand));
    }
}
