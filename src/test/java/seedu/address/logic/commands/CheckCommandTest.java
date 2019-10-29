package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.model.Model;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.versiontracking.CommitList;

public class CheckCommandTest {
    private class StudyPlanStub extends StudyPlan {
        private int stubSwitch;
        public StudyPlanStub(int stubSwitch) {
            super(new ModulesInfo(), null);
            this.stubSwitch = stubSwitch;
        }

        @Override
        public int getTotalMcCount() {
            switch (stubSwitch) {
            case 1:
                return 10;
            case 2:
                return 200;
            default:
                return 0;
            }
        }

        @Override
        public int getNumCoreModules() {
            switch (stubSwitch) {
            case 1:
                return 5;
            case 2:
                return 15;
            default:
                return 0;
            }
        }

        @Override
        public HashMap<String, Integer> getFocusPrimaries() {
            HashMap<String, Integer> map = new HashMap<>();
            switch (stubSwitch) {
            case 1:
                map.put("AI:P", 0);
                map.put("Algo:P", 1);
                map.put("CGG:P", 0);
                map.put("ComSec:P", 0);
                map.put("DB:P", 0);
                map.put("MIR:P", 2);
                map.put("NDS:P", 0);
                map.put("PL:P", 1);
                map.put("Para:P", 2);
                map.put("SE:P", 0);
                break;
            case 2:
                map.put("AI:P", 0);
                map.put("Algo:P", 2);
                map.put("CGG:P", 0);
                map.put("ComSec:P", 1);
                map.put("DB:P", 3);
                map.put("MIR:P", 2);
                map.put("NDS:P", 0);
                map.put("PL:P", 1);
                map.put("Para:P", 2);
                map.put("SE:P", 0);
                break;
            default:
                break;
            }
            return map;
        }
    }

    /*
     * We define three model stubs: ModelStubNull, ModelStubOne, and ModelStubTwo.
     * All are meant to be called only on the method `getActiveStudyPlan`.
     */

    private class ModelStubNull implements Model {
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
            return null;
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
        public List<String> getValidMods(SemesterName semName) {
            throw new AssertionError("This method should not be called.");
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
        public boolean canUndoModulePlanner() {
            return false;
        }

        @Override
        public boolean canRedoModulePlanner() {
            return false;
        }

        @Override
        public void undoModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToHistory() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubOne implements Model {
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
            return new StudyPlanStub(1);
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
        public List<String> getValidMods(SemesterName semName) {
            throw new AssertionError("This method should not be called.");
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
        public boolean canUndoModulePlanner() {
            return false;
        }

        @Override
        public boolean canRedoModulePlanner() {
            return false;
        }

        @Override
        public void undoModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToHistory() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubTwo implements Model {
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
            return new StudyPlanStub(2);
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
        public List<String> getValidMods(SemesterName semName) {
            throw new AssertionError("This method should not be called.");
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
        public boolean canUndoModulePlanner() {
            return false;
        }

        @Override
        public boolean canRedoModulePlanner() {
            return false;
        }

        @Override
        public void undoModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoModulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToHistory() {
            throw new AssertionError("This method should not be called.");
        }
    }

    // ======================== TESTS ================================
    @Test
    public void execute_check_nullActiveStudyPlan() {
        Model model = new CheckCommandTest.ModelStubNull();
        CommandResult expectedCommandResult = new CommandResult("You do not have a study plan!", false, false);
        assertCommandSuccess(new CheckCommand(), model, expectedCommandResult, model);
    }

    @Test
    public void execute_check_failCheckActiveStudyPlan() {
        Model model = new CheckCommandTest.ModelStubOne();
        String expected = "Graduation requirements have not been fulfilled.\n"
                + "MCs: 10/108\n"
                + "Number of core modules taken: 5/15\n"
                + "Number of focus area primaries taken:\n"
                + "[AI:P]: 0\n"
                + "[Algo:P]: 1\n"
                + "[CGG:P]: 0\n"
                + "[ComSec:P]: 0\n"
                + "[DB:P]: 0\n"
                + "[MIR:P]: 2\n"
                + "[NDS:P]: 0\n"
                + "[PL:P]: 1\n"
                + "[Para:P]: 2\n"
                + "[SE:P]: 0\n";
        CommandResult expectedCommandResult = new CommandResult(expected, false, false);
        assertCommandSuccess(new CheckCommand(), model, expectedCommandResult, model);
    }

    @Test
    public void execute_check_passCheckActiveStudyPlan() {
        Model model = new CheckCommandTest.ModelStubTwo();
        String expected = "All graduation requirements have been fulfilled!\n"
                + "MCs: 200/108\n"
                + "Number of core modules taken: 15/15\n"
                + "Number of focus area primaries taken:\n"
                + "[AI:P]: 0\n"
                + "[Algo:P]: 2\n"
                + "[CGG:P]: 0\n"
                + "[ComSec:P]: 1\n"
                + "[DB:P]: 3\n"
                + "[MIR:P]: 2\n"
                + "[NDS:P]: 0\n"
                + "[PL:P]: 1\n"
                + "[Para:P]: 2\n"
                + "[SE:P]: 0\n";
        CommandResult expectedCommandResult = new CommandResult(expected, false, false);
        assertCommandSuccess(new CheckCommand(), model, expectedCommandResult, model);
    }

    @Test
    public void equals() {
        CheckCommand x = new CheckCommand();
        CheckCommand y = new CheckCommand();
        assertEquals(x, y);
        assertEquals(x, x);
    }
}
