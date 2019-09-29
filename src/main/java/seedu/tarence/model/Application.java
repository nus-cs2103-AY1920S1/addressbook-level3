package seedu.tarence.model;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.module.UniqueModuleList;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.person.UniquePersonList;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.UniqueTutorialList;

/**
 * Wraps all data at the application level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Application implements ReadOnlyApplication {

    private final UniquePersonList persons;
    private final UniqueModuleList modules;
    private final UniqueTutorialList tutorials;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        modules = new UniqueModuleList();
        tutorials = new UniqueTutorialList();
    }

    public Application() {}

    /**
     * Creates an application using the Persons in the {@code toBeCopied}
     */
    public Application(ReadOnlyApplication toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// ============================== list overwrite operations    =================================================

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code Application} with {@code newData}.
     */
    public void resetData(ReadOnlyApplication newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    ////=================== person-level operations    =================================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the application.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the application.
     * The person must not already exist in the application.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the application.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code Application}.
     * {@code key} must exist in the application.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Adds a student to their associated tutorial in its associated module.
     */
    public void addStudentToTutorial(Student student) {
        requireNonNull(student);
        Module targetModule = null;
        for (Module module : modules) {
            if (module.getModCode().equals(student.getModCode())) {
                targetModule = module;
                break;
            }
        }
        for (Tutorial tutorial : targetModule.getTutorials()) {
            if (tutorial.getTutName().equals(student.getTutName())) {
                tutorial.addStudent(student);
                break;
            }
        }
    }

    ////=================== module-level operations    =================================================================

    /**
     * Adds a module to the application.
     * The module must not already exist in the application.
     */
    public void addModule(Module newModule) {
        requireNonNull(newModule);
        modules.add(newModule);
    }

    /**
     * Returns true if a module with the same identity as {@code module} exists in the application.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Returns true if a module of the given code exists. Used to check whether a tutorial command contains a valid
     * module code.
     */
    public boolean hasModuleOfCode(ModCode modCode) {
        requireNonNull(modCode);
        boolean hasMod = false;
        for (Module module :  modules) {
            if (module.getModCode().equals(modCode)) {
                hasMod = true;
                break;
            }
        }
        return hasMod;
    }

    public void addTutorialToModule(Tutorial tutorial) {
        requireNonNull(tutorial);
        Module targetModule = null;
        for (Module module : modules) {
            if (module.getModCode().equals(tutorial.getModCode())) {
                targetModule = module;
                break;
            }
        }
        targetModule.addTutorial(tutorial);
    }
    ////=================== tutorial-level operations    ==============================================================

    /**
     * Adds a tutorial to the application.
     * The tutorial must not already exist in the application.
     */
    public void addTutorial(Tutorial newTutorial) {
        requireNonNull(newTutorial);
        tutorials.add(newTutorial);
    }

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the application.
     */
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorials.contains(tutorial);
    }

    /**
     * Returns true if a module with a tutorial of the given name exists. Used to check whether a student command
     * contains a valid module code and tutorial name.
     */
    public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
        requireAllNonNull(modCode, tutName);
        boolean hasMod = false;
        for (Module module : modules) {
            if (module.getModCode().equals(modCode)) {
                hasMod = true;
                break;
            }
        }
        if (!hasMod) {
            return false;
        }
        boolean hasTut = false;
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutName().equals(tutName)) {
                hasTut = true;
                break;
            }
        }
        return hasTut;
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Application // instanceof handles nulls
                && persons.equals(((Application) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }


}
