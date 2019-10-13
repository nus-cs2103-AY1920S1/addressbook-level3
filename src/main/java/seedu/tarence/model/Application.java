package seedu.tarence.model;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import javafx.collections.ObservableList;

import seedu.tarence.logic.commands.Command;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.module.UniqueModuleList;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.person.UniquePersonList;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.UniqueTutorialList;
import seedu.tarence.model.tutorial.Week;

/**
 * Wraps all data at the application level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Application implements ReadOnlyApplication {

    private final UniquePersonList persons;
    private final UniquePersonList students;
    private final UniqueModuleList modules;
    private final UniqueTutorialList tutorials;

    private Stack<Command> pendingCommands;
    private List<Command> suggestedCommands;
    private String suggestedCorrections;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        students = new UniquePersonList();
        modules = new UniqueModuleList();
        tutorials = new UniqueTutorialList();
        pendingCommands = new Stack<>();
        suggestedCommands = new ArrayList<>();
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
     * Replaces the contents of the student list with {@code students}.
     * {@code persons} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        List<Person> personList = new ArrayList<>();
        for (Student student : students) {
            personList.add(student);
        }
        this.students.setPersons(personList);
    }

    /**
     * Replaces the contents of the module list with {@code Module}.
     * {@code Module} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the tutorials list with {@code Tutorial}.
     * {@code Tutorial} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials.setTutorials(tutorials);
    }

    /**
     * Resets the existing data of this {@code Application} with {@code newData}.
     */
    public void resetData(ReadOnlyApplication newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setStudents(newData.getStudentList());
        setModules(newData.getModuleList());
        setTutorials(newData.getTutorialList());
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

    ////=================== student-level operations    ================================================================
    /**
     * Returns true if a student with the same identity as {@code student} exists in the application.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the application.
     * The student must not already exist in the application.
     */
    public void addStudent(Student s) {
        students.add(s);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedStudent} must not be the same as another existing student in the application.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);
        //students.setPerson(target, editedStudent);
        removeStudent(target);

        addStudent(editedStudent);
        addStudentToTutorial(editedStudent);
        /*
        // Modify tutorial level
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutName().equals(target.getTutName())) {
                tutorial.setStudent(target, editedStudent);
            }
        }

        // Modify Module level: TODO - refactor
        for (Module module : modules) {
            if (module.getModCode().equals(target.getModCode())) {
                Module targetMod = module;
                for (Tutorial tutorial : targetMod.getTutorials()) {
                    if (tutorial.getTutName().equals(target.getTutName())) {
                        tutorial.setStudent(target, editedStudent);
                    }
                }
            }
        }

         */
    }

    /**
     * Removes {@code key} from this {@code Application}.
     * {@code key} must exist in the application.
     */
    public void removeStudent(Student key) {
        // Delete students from the main list
        students.remove(key);


        // Delete students from existing tutorials
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutName().equals(key.getTutName())) {
                tutorial.deleteStudent(key);
            }
        }

        // Delete students from existing modules
        for (Module module : modules) {
            if (module.getModCode().equals(key.getModCode())) {
                module.deleteStudent(key);
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
        return modules.getModuleByCode(modCode).isPresent();
    }

    /**
     * Deletes a module from the application. Assumes the module exists in the application.
     */
    public void removeModule(Module module) {
        requireNonNull(module);
        modules.remove(module);
    }

    /**
     * Deletes all tutorials in a given module from the application.
     */
    public void removeTutorialsFromModule(Module module) {
        for (Tutorial tutorial : module.getTutorials()) {
            removeStudentsFromTutorial(tutorial);
            tutorials.remove(tutorial);
        }
    }

    /**
     * Adds a tutorial to its associated module. Assumes that a module of the given code exists.
     */
    public void addTutorialToModule(Tutorial tutorial) {
        requireNonNull(tutorial);
        Module targetModule = modules.getModuleByCode(tutorial.getModCode()).get();
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
        Optional<Module> module = modules.getModuleByCode(modCode);
        if (module.isEmpty()) {
            return false;
        }
        boolean hasTut = false;
        for (Tutorial tutorial : module.get().getTutorials()) {
            if (tutorial.getTutName().equals(tutName)) {
                hasTut = true;
                break;
            }
        }
        return hasTut;
    }

    /**
     * Returns number of tutorials with equal names exists in the application.
     */
    public int getNumberOfTutorialsOfName(TutName tutName) {
        requireNonNull(tutName);
        int tutCount = 0;
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutName().equals(tutName)) {
                tutCount++;
            }
        }
        return tutCount;
    }

    /**
     * Deletes a tutorial from the application. Assumes the tutorial exists.
     */
    public void removeTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        tutorials.remove(tutorial);

        // Delete from existing modules
        for (Module module : modules) {
            if (module.getModCode().equals(tutorial.getModCode())) {
                module.deleteTutorial(tutorial);
            }
        }
    }

    public void setAttendance(Tutorial tutorial, Week week, Student student) {
        requireAllNonNull(tutorial, week, student);
        tutorial.setAttendance(week, student);
    }

    //// util methods

    /**
     * Deletes all students from the given tutorial.
     */
    public void removeStudentsFromTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        for (Student student : tutorial.getStudents()) {
            students.remove(student);
        }
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
    public ObservableList<Student> getStudentList() {
        return (ObservableList<Student>) (ObservableList<?>) students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tutorial> getTutorialList() {
        return tutorials.asUnmodifiableObservableList();
    }

    /**
     * Stores a command for later execution, pending user confirmation.
     */
    public void storePendingCommand(Command command) {
        pendingCommands.push(command);
    }

    /**
     * Removes pending command from application and returns it for execution.
     */
    public Command retrievePendingCommand() {
        if (hasPendingCommand()) {
            return pendingCommands.pop();
        } else {
            return null;
        }
    }

    /**
     * Checks whether a pending command exists in the application.
     */
    public boolean hasPendingCommand() {
        return pendingCommands.size() > 0;
    }

    /**
     * Returns the pending command at the top of the execution stack if it exists, else null.
     */
    public Command peekPendingCommand() {
        if (hasPendingCommand()) {
            return pendingCommands.peek();
        } else {
            return null;
        }
    }

    /**
     * Stores a list of suggested commands for future selection and execution.
     */
    void storeSuggestedCommands(List<Command> suggestedCommands, String suggestedCorrections) {
        this.suggestedCommands = suggestedCommands;
        this.suggestedCorrections = suggestedCorrections;
    }

    /**
     * Gets the stored list of suggested commands for selection and execution.
     */
    List<Command> getSuggestedCommands() {
        return suggestedCommands;
    }

    /**
     * Gets the string representing the corrections in the suggested commands.
     */
    String getSuggestedCorrections() {
        return suggestedCorrections;
    }

    /**
     * Deletes the stored list of suggested commands.
     */
    void deleteSuggestedCommands() {
        suggestedCommands = null;
        suggestedCorrections = null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Application // instanceof handles nulls
                && persons.equals(((Application) other).persons)
                && students.equals(((Application) other).students)
                && modules.equals(((Application) other).modules)
                && tutorials.equals(((Application) other).tutorials));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
