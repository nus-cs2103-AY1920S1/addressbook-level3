package seedu.address.logic.commands.verification;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandException;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
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
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.versiontracking.CommitList;

public class DescriptionCommandTest {

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
            throw new AssertionError("This method should not be called.");
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
            if ("CS2040S".equals(moduleCode)) {
                return "CS2040S module information";
            } else {
                return null;
            }
        }

        @Override
        public List<String> getValidMods(SemesterName semName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean semesterHasModule(String moduleCode, SemesterName semesterName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePrereqs() {
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
        public boolean semesterHasUe(SemesterName semesterName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void renameUeInSemester(SemesterName semesterName, String moduleCode) {
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
        public boolean addTagToActiveSp(UserTag tag, String moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean activeSpContainsTag(String tagName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTagFromActiveSp(UserTag toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTagFromAllModulesInActiveSp(UserTag toRemove) {
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
        public Tag getTagFromActiveSp(String tagName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueTagList getTagsFromActiveSp() {
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
    }

    @Test
    public void execute_description_success() {
        Model model = new ModelStub();
        CommandResult expectedCommandResult = new CommandResult("CS2040S module information", false, false);
        assertCommandSuccess(new DescriptionCommand("CS2040S"), model, expectedCommandResult, model);
    }

    @Test
    public void execute_description_failure() {
        Model model = new ModelStub();
        assertCommandException(new DescriptionCommand("CS0000"), model, MESSAGE_INVALID_MODULE);
    }
}
