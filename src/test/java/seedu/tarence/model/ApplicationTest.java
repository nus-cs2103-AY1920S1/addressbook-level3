package seedu.tarence.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalModules.CS1101S;
import static seedu.tarence.testutil.TypicalPersons.ALICE;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.module.exceptions.DuplicateModuleException;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.person.exceptions.DuplicatePersonException;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.ModuleBuilder;
import seedu.tarence.testutil.PersonBuilder;

public class ApplicationTest {

    private final Application application = new Application();

    @Test
    public void personListconstructor() {
        assertEquals(Collections.emptyList(), application.getPersonList());
    }

    @Test
    public void moduleListconstructor() {
        assertEquals(Collections.emptyList(), application.getModuleList());
    }

    @Test
    public void tutorialListconstructor() {
        assertEquals(Collections.emptyList(), application.getTutorialList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> application.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyApplication_replacesData() {
        Application newData = getTypicalApplication();
        application.resetData(newData);
        assertEquals(newData, application);
    }

    ////=================== person-level operations    =================================================================

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ApplicationStub newData = new ApplicationStub();
        newData.setPersons(newPersons);

        assertThrows(DuplicatePersonException.class, () -> application.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> application.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInApplication_returnsFalse() {
        assertFalse(application.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInApplication_returnsTrue() {
        application.addPerson(ALICE);
        assertTrue(application.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInApplication_returnsTrue() {
        application.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(application.hasPerson(editedAlice));
    }

    ////=================== module-level operations    =================================================================
    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two Modules with the same identity fields
        Module editedCS1101S = new ModuleBuilder(CS1101S).build();
        List<Module> newModules = Arrays.asList(CS1101S, editedCS1101S);
        ApplicationStub newData = new ApplicationStub();
        newData.setModules(newModules);

        assertThrows(DuplicateModuleException.class, () -> application.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> application.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInApplication_returnsFalse() {
        assertFalse(application.hasModule(CS1101S));
    }

    @Test
    public void hasModule_moduleInApplication_returnsTrue() {
        application.addModule(CS1101S);
        assertTrue(application.hasModule(CS1101S));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInApplication_returnsTrue() {
        application.addModule(CS1101S);
        Module editedCS1101S = new ModuleBuilder(CS1101S).build();
        assertTrue(application.hasModule(editedCS1101S));
    }

    ////=================== tutorial-level operations    ===============================================================
    // TODO: public void resetData_withDuplicateTutorials_throwsDuplicateTutorialException()

    @Test
    public void hasTutorial_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> application.hasTutorial(null));
    }

    // TODO: hasTutorial_TutorialNotInApplication_returnsFalse

    // TODO: hasTutorial_TutorialInApplication_returnsTrue

    // TODO: hasTutorial_tutorialWithSameIdentityFieldsInApplication_returnsTrue

    /**
     * A stub ReadOnlyApplication whose persons list can violate interface constraints.
     */
    private static class ApplicationStub implements ReadOnlyApplication {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<Tutorial> tutorials = FXCollections.observableArrayList();

        public void setPersons(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        public void setModules(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        public void setTutorials(Collection<Tutorial> tutorials) {
            this.tutorials.setAll(tutorials);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
        @Override
        public ObservableList<Tutorial> getTutorialList() {
            return tutorials;
        }
    }

}
