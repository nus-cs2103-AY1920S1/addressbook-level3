package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UnblockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveAllTagsCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.datamanagement.SortStudyPlansByPriorityTagCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.CollapseAllCommand;
import seedu.address.logic.commands.gui.CollapseCommand;
import seedu.address.logic.commands.gui.ExpandAllCommand;
import seedu.address.logic.commands.gui.ExpandCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.AddSemesterCommand;
import seedu.address.logic.commands.storage.CommitStudyPlanCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.DefaultStudyPlanCommand;
import seedu.address.logic.commands.storage.DeleteCommitCommand;
import seedu.address.logic.commands.storage.DeleteSemesterCommand;
import seedu.address.logic.commands.storage.DeleteStudyPlanCommand;
import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.RevertCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.storage.ViewStudyPlanCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.commands.verification.ClearInvalidModsCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.model.ModulePlanner;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModule;
import seedu.address.testutil.TypicalStudyPlans;

class ModulePlannerAutocompleteSearchTest {
    private static final String NON_EXISTENT_COMMAND_WORD = "abcdefgh";

    @Test
    public void constructor_nullModulePlanner_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModulePlannerAutocompleteSearch(null));
    }

    @Test
    public void handleChangeOfActiveStudyPlan() throws CloneNotSupportedException {
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        StudyPlan studyPlanOne = new StudyPlanBuilder().withIndex(1).build();
        StudyPlan studyPlanTwo = new StudyPlanBuilder().withIndex(2).withModuleTags(validTagOne).build();
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlans(studyPlanOne.clone(),
                studyPlanTwo.clone()).build();
        modulePlanner.activateFirstStudyPlan();

        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);
        List<String> searchResults = autocompleteSearch.getSearchResults(" " + validTagNameOne);
        assertEquals(0, searchResults.size());

        modulePlanner.activateStudyPlan(2);
        searchResults = autocompleteSearch.getSearchResults(" " + validTagNameOne);
        assertEquals(0, searchResults.size());

        autocompleteSearch.handleChangeOfActiveStudyPlan();
        searchResults = autocompleteSearch.getSearchResults(" " + validTagNameOne);
        assertEquals(1, searchResults.size());


        Tag validTagTwo = new TagBuilder().buildUserTag("differentTestTag");
        String validTagNameTwo = validTagTwo.getTagName();

        searchResults = autocompleteSearch.getSearchResults(" " + validTagNameTwo);
        assertEquals(0, searchResults.size());

        modulePlanner.addTagToActiveSp((UserTag) validTagTwo, TypicalModule.CS1101S.getModuleCode().toString());

        searchResults = autocompleteSearch.getSearchResults(" " + validTagNameTwo);
        assertEquals(validTagNameTwo, searchResults.get(0));

        searchResults = autocompleteSearch.getSearchResults("test test " + validTagNameTwo);
        assertEquals(validTagNameTwo, searchResults.get(0));
    }

    @Test
    void getSearchResults_null_throwsNullPointerException() {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();
        modulePlanner.activateFirstStudyPlan();
        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);
        assertThrows(NullPointerException.class, () -> autocompleteSearch.getSearchResults(null));
    }

    @Test
    void getSearchResults_commandDoesNotExist_failure() {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();
        modulePlanner.activateFirstStudyPlan();
        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);

        List<String> searchResults = autocompleteSearch.getSearchResults(NON_EXISTENT_COMMAND_WORD);
        assertEquals(0, searchResults.size());
    }

    @Test
    void getSearchResults_oneCommand_success() {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();
        modulePlanner.activateFirstStudyPlan();
        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);
        checkOneCommand(AddModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(BlockCurrentSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DeleteModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(RedoCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(SetCurrentSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(UnblockCurrentSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(UndoCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DeleteTagCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(FindModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(RemoveAllTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(RemoveTagFromModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(RemoveTagFromStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(RenameTagCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(SortStudyPlansByPriorityTagCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(TagModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewAllTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewDefaultTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewModuleTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewTaggedCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(CollapseAllCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(CollapseCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ExpandAllCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ExpandCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(HelpCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ActivateStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(AddSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(CommitStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(CreateStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DefaultStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DeleteCommitCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DeleteSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DeleteStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(EditTitleCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ListAllStudyPlansCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(RevertCommitCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewCommitCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewCommitHistoryCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ViewStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(CheckCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ClearInvalidModsCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(DescriptionCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ValidModsCommand.COMMAND_WORD, autocompleteSearch);
        checkOneCommand(ExitCommand.COMMAND_WORD, autocompleteSearch);
    }

    @Test
    void getSearchResults_helpCommands_success() {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();
        modulePlanner.activateFirstStudyPlan();
        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);
        checkHelpCommand(AddModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(BlockCurrentSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DeleteModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(RedoCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(SetCurrentSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(UnblockCurrentSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(UndoCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DeleteTagCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(FindModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(RemoveAllTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(RemoveTagFromModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(RemoveTagFromStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(RenameTagCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(SortStudyPlansByPriorityTagCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(TagModuleCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewAllTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewDefaultTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewModuleTagsCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewTaggedCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(CollapseAllCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(CollapseCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ExpandAllCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ExpandCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(HelpCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ActivateStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(AddSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(CommitStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(CreateStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DefaultStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DeleteCommitCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DeleteSemesterCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DeleteStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(EditTitleCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ListAllStudyPlansCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(RevertCommitCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewCommitCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewCommitHistoryCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ViewStudyPlanCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(CheckCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ClearInvalidModsCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(DescriptionCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ValidModsCommand.COMMAND_WORD, autocompleteSearch);
        checkHelpCommand(ExitCommand.COMMAND_WORD, autocompleteSearch);
    }

    void checkOneCommand(String commandWord, ModulePlannerAutocompleteSearch autocompleteSearch) {
        List<String> searchResults = autocompleteSearch.getSearchResults(commandWord);
        assertEquals(commandWord, searchResults.get(0));
    }

    void checkHelpCommand(String commandWord, ModulePlannerAutocompleteSearch autocompleteSearch) {
        List<String> searchResults = autocompleteSearch.getSearchResults(HelpCommand.COMMAND_WORD + " " + commandWord);
        assertEquals(commandWord, searchResults.get(0));
    }

    @Test
    void getSearchResults_tags_success() {
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        StudyPlan studyPlan = new StudyPlanBuilder().build();
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();
        modulePlanner.activateFirstStudyPlan();

        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);
        List<String> searchResults = autocompleteSearch.getSearchResults(" " + validTagNameOne);
        assertEquals(0, searchResults.size());

        modulePlanner.addTagToActiveSp((UserTag) validTagOne, TypicalModule.CS1101S.getModuleCode().toString());

        searchResults = autocompleteSearch.getSearchResults(" " + validTagNameOne);
        assertEquals(validTagNameOne, searchResults.get(0));

        searchResults = autocompleteSearch.getSearchResults("test test " + validTagNameOne);
        assertEquals(validTagNameOne, searchResults.get(0));
    }

    @Test
    void getSearchResults_semester_success() {
        StudyPlan studyPlan = TypicalStudyPlans.SP_1;
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();
        modulePlanner.activateFirstStudyPlan();

        ModulePlannerAutocompleteSearch autocompleteSearch =
                new ModulePlannerAutocompleteSearch(modulePlanner);
        List<String> searchResults = autocompleteSearch.getSearchResults(" " + SemesterName.Y1S1.name());
        assertEquals(SemesterName.Y1S1.name(), searchResults.get(0));

        searchResults = autocompleteSearch.getSearchResults("test test " + SemesterName.Y1S1.name());
        assertEquals(SemesterName.Y1S1.name(), searchResults.get(0));
    }
}
