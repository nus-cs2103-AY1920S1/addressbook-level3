package seedu.address.logic.commands;

//import static java.util.Objects.requireNonNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

//import javafx.collections.ObservableList;
//import seedu.address.commons.core.GuiSettings;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
// import seedu.address.model.ModulePlanner;
//import seedu.address.model.ReadOnlyModulePlanner;
//import seedu.address.model.ReadOnlyUserPrefs;

import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;

public class CreateStudyPlanCommandTest {

    //TODO implement tests

    @Test
    public void constructor_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateStudyPlanCommand(null));
    }

    @Test
    public void execute_studyPlanAcceptedByModel_addSuccessful() throws Exception {
        //ModelStubAcceptingStudyPlanAdded modelStub = new ModelStubAcceptingStudyPlanAdded();
        StudyPlan validStudyPlan = new StudyPlanBuilder().build();

        //CommandResult commandResult = new CreateStudyPlanCommand(validStudyPlan).execute(modelStub);

        //assertEquals(String.format(CreateStudyPlanCommand.MESSAGE_SUCCESS, validStudyPlan),
        //      commandResult.getFeedbackToUser());
        //assertEquals(Arrays.asList(validStudyPlan), modelStub.studyPlansAdded);
    }

    @Test
    public void execute_duplicateStudyPlan_throwsCommandException() {
        StudyPlan validStudyPlan = new StudyPlanBuilder().build();
        CreateStudyPlanCommand addCommand = new CreateStudyPlanCommand(validStudyPlan);
        //ModelStub modelStub = new ModelStubWithStudyPlan(validStudyPlan);

        //assertThrows(CommandException.class, CreateStudyPlanCommand.MESSAGE_DUPLICATE_STUDYPLAN,
        //      () -> addCommand.execute(modelStub));
    }

    /*
    @Test
    public void equals() {
        StudyPlan alice = new StudyPlanBuilder().withName("Alice").build();
        StudyPlan bob = new StudyPlanBuilder().withName("Bob").build();
        CreateStudyPlanCommand addAliceCommand = new CreateStudyPlanCommand(alice);
        CreateStudyPlanCommand addBobCommand = new CreateStudyPlanCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateStudyPlanCommand addAliceCommandCopy = new CreateStudyPlanCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different studyPlan -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
    */

    /**
     * A default model stub that have all of the methods failing.
     */
    /*
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
    }
    */
    /**
     * A Model stub that contains a single studyPlan.
     */
    /*
    private class ModelStubWithStudyPlan extends ModelStub {
        private final StudyPlan studyPlan;

        ModelStubWithStudyPlan(StudyPlan studyPlan) {
            requireNonNull(studyPlan);
            this.studyPlan = studyPlan;
        }

        @Override
        public boolean hasStudyPlan(StudyPlan studyPlan) {
            requireNonNull(studyPlan);
            return this.studyPlan.isSameStudyPlan(studyPlan);
        }
    }
    */

    /**
     * A Model stub that always accept the studyPlan being added.
     */
    /*
    private class ModelStubAcceptingStudyPlanAdded extends ModelStub {
        final ArrayList<StudyPlan> studyPlansAdded = new ArrayList<>();

        @Override
        public boolean hasStudyPlan(StudyPlan studyPlan) {
            requireNonNull(studyPlan);
            return studyPlansAdded.stream().anyMatch(studyPlan::isSameStudyPlan);
        }

        @Override
        public void addStudyPlan(StudyPlan studyPlan) {
            requireNonNull(studyPlan);
            studyPlansAdded.add(studyPlan);
        }

        @Override
        public ReadOnlyModulePlanner getModulePlanner() {
            return new ModulePlanner();
        }
    }
     */

}
