package seedu.address.logic.commands.verification;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS3244;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TypicalModule;
import seedu.address.ui.ResultViewType;

public class ValidModsCommandTest {

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unblockSemester(SemesterName sem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getModulePlannerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModulePlannerFilePath(Path modulePlannerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModulePlanner(ReadOnlyModulePlanner modulePlanner) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModulePlanner getModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudyPlan(StudyPlan studyPlan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudyPlan getActiveStudyPlan() {
            return (new StudyPlanBuilder()).build();
        }

        @Override
        public boolean activateFirstStudyPlan() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudyPlan activateStudyPlan(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudyPlan(StudyPlan target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudyPlan(StudyPlan studyPlan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<StudyPlan> getFilteredStudyPlanList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudyPlanList(Predicate<StudyPlan> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeActiveStudyPlanTitle(String title) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitActiveStudyPlan(String commitMessage) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CommitList getCommitListByStudyPlanIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudyPlanCommitManagerByIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void revertToCommit(int studyPlanIndex, int commitNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCommit(int studyPlanIndex, int commitNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isValidModuleCode(String moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ModulesInfo getModulesInfo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getModuleInformation(String moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> getValidMods(SemesterName semName) {
            if (semName.equals(SemesterName.Y1S1)) {
                ArrayList<Module> arr = new ArrayList<>();
                arr.add(CS1101S);
                arr.add(TypicalModule.CS3244);
                return arr;
            } else {
                return null;
            }
        }

        @Override
        public boolean semesterHasModule(String moduleCode, SemesterName semesterName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refresh() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(String moduleCode, SemesterName sem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void blockSemester(SemesterName sem, String reason) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModule(String moduleCode, SemesterName semesterName) {
            throw new AssertionError("This method should not be called.");
        }



        @Override
        public void setSemester(SemesterName semester) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Semester getSemester(SemesterName semesterName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean addModuleTagToActiveSp(UserTag tag, String moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudyPlanTagToSp(Tag tag, int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeStudyPlanTagFromSp(Tag tag, int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replaceTagInActiveSp(Tag original, Tag replacement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean spContainsPriorityTag(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PriorityTag getPriorityTagFromSp(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean activeSpContainsModuleTag(String tagName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean spContainsStudyPlanTag(String tagName, int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModuleTagFromActiveSp(UserTag toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean removeTagFromAllModulesInActiveSp(UserTag toRemove) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean removeTagFromModuleInActiveSp(UserTag toRemove, String moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAllCompletedTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Tag getModuleTagFromActiveSp(String tagName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueTagList getModuleTagsFromActiveSp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueTagList getModuleTagsFromActiveSp(String moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HashMap<String, Module> getModulesFromActiveSp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueSemesterList getSemestersFromActiveSp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudyPlan getStudyPlan(int index) {
            throw new AssertionError("This method should not be called.");
        }

        //@Override
        public void deleteAllModulesInSemester(SemesterName semesterName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSemester(SemesterName semesterName) {

        }

        @Override
        public boolean canUndoModulePlanner() {
            return false;
        }

        @Override
        public boolean canRedoModulePlanner() {
            return false;
        }

        @Override
        public void undoModulePlanner() {

        }

        @Override
        public void redoModulePlanner() {

        }

        @Override
        public void addToHistory() {

        }

        @Override
        public int clearInvalidMods() {
            throw new AssertionError("This method should not be called.");
        }
    }

    @Test
    public void execute_description_success() {
        Model model = new ValidModsCommandTest.ModelStub();
        UniqueModuleList modList = new UniqueModuleList();
        modList.add(CS1101S);
        modList.add(CS3244);
        CommandResult expectedCommandResult = new CommandResult("All valid modules in Y1S1 are shown",
                ResultViewType.MODULE, modList.asUnmodifiableObservableList());
        assertCommandSuccess(new ValidModsCommand(SemesterName.Y1S1), model, expectedCommandResult, model);
    }
}
