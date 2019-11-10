package seedu.address.logic.cap.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.ReadOnlyUserPrefs;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.common.Module;
import seedu.address.testutil.ModuleBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validModule), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        AddCommand addCommand = new AddCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MODULE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Module cs2103 = new ModuleBuilder().withModuleCode("CS2103").build();
        Module cs2100 = new ModuleBuilder().withModuleTitle("Software Programming").build();
        AddCommand addCS2103Command = new AddCommand(cs2103);
        AddCommand addCS2100Command = new AddCommand(cs2100);

        // same object -> returns true
        assertTrue(addCS2103Command.equals(addCS2103Command));

        // same values -> returns true
        AddCommand addCS2103CommandCopy = new AddCommand(cs2103);
        assertTrue(addCS2103Command.equals(addCS2103CommandCopy));

        // different types -> returns false
        assertFalse(addCS2103Command.equals(1));

        // null -> returns false
        assertFalse(addCS2103Command.equals(null));

        // different module -> returns false
        assertFalse(addCS2103Command.equals(addCS2100Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setCapUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getCapUserPrefs() {
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
        public Path getCapLogFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCapLogFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSemester(Semester module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCapLog(ReadOnlyCapLog newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCapLog getCapLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Module> getFilteredListbyTime() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSortedList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSemester(Semester module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSemester(Semester target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSemester(Semester target, Semester editedSemester) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Semester> getFilteredSemesterList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSemesterList(Predicate<Semester> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getModuleCount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public double getFilteredCapInformation() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public double getFilteredMcInformation() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<PieChart.Data> getFilteredGradeCounts() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean upRank() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean downRank() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Image getRankImage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getRankTitle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRank(double rank) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyCapLog getCapLog() {
            return new CapLog();
        }
    }

}
