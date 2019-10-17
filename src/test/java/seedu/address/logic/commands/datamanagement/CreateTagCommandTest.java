package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.model.Model;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;

public class CreateTagCommandTest {

    // Incomplete
    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateStudyPlanCommand(null));
    }

    @Test
    public void execute_tagAcceptedByModel_addSuccessful() throws Exception {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        ModelStubWithStudyPlan modelStub = new ModelStubWithStudyPlan(studyPlan);
        Tag validTag = new TagBuilder().buildTestUserTag();
        String validTagName = validTag.getTagName();

        CommandResult commandResult = new CreateTagCommand(validTagName).execute(modelStub);
        assertEquals(String.format(CreateTagCommand.MESSAGE_SUCCESS, validTag),
              commandResult.getFeedbackToUser());

        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.initDefaultTags();
        uniqueTagList.addTag(validTag);
        assertEquals(uniqueTagList.asUnmodifiableObservableList(), studyPlan.getTags().asUnmodifiableObservableList());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
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
        public void addStudyPlan(StudyPlan studyPlan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModulePlanner(ReadOnlyModulePlanner newData) {
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
        public void deleteStudyPlan(StudyPlan target) {
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
            throw new AssertionError("This method should not be called");
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
        public StudyPlan activateStudyPlan(int i) {
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
        public boolean semesterHasModule(String moduleCode, SemesterName semesterName) {
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
        public void commitActiveStudyPlan(String commitMessage) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudyPlanCommitManagerByIndex(int i) {
            throw new AssertionError("This method should not be called");
        }


        @Override
        public CommitList getCommitListByStudyPlanIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePrereqs() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single studyPlan.
     */
    private class ModelStubWithStudyPlan extends ModelStub {
        private final StudyPlan studyPlan;

        public ModelStubWithStudyPlan(StudyPlan studyPlan) {
            requireNonNull(studyPlan);
            this.studyPlan = studyPlan;
        }

        @Override
        public StudyPlan getActiveStudyPlan() {
            return this.studyPlan;
        }
    }
}
