package seedu.tarence.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.ModelStub;
import seedu.tarence.logic.parser.PartialInput;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.TypicalStudents;


class AutocompleteHandlerTest {

    public static final Student AMY = TypicalStudents.AMY;
    public static final Student ALICE = TypicalStudents.ALICE;
    public static final Student BOB = TypicalStudents.BOB;

    private AutocompleteHandler autocompleteHandler;

    @BeforeEach
    void setup() {
        Model model = new ModelStubAutocompleteHandler();
        autocompleteHandler = new AutocompleteHandler(model);
        model.addStudent(AMY);
        model.addStudent(ALICE);
        model.addStudent(BOB);

        Module moduleAmy = new Module(AMY.getModCode(), new ArrayList<>());
        Module moduleBob = new Module(BOB.getModCode(), new ArrayList<>());
        model.addModule(moduleAmy);
        model.addModule(moduleBob);

        Tutorial tutorialAmy = new TutorialBuilder().withModCode(AMY.getModCode())
                .withTutName(AMY.getTutName().toString()).build();
        Tutorial tutorialBob = new TutorialBuilder().withModCode(BOB.getModCode())
                .withTutName(BOB.getTutName().toString()).build();
        model.addTutorial(tutorialAmy);
        model.addTutorial(tutorialBob);
    }

    @Test
    void handle_autocompleteName_suggestionsProvided() throws ParseException {
        String input = "someCommand n/A";
        assertEquals(autocompleteHandler.handle(input), "someCommand n/" + AMY.getName().toString());
        assertEquals(autocompleteHandler.handle(input), "someCommand n/" + ALICE.getName().toString());
    }

    @Test
    void handle_autocompleteInvalidName_noSuggestionsFound() {
        String input = "someCommand n/C";
        assertThrows(IndexOutOfBoundsException.class, () -> autocompleteHandler.handle(input));
    }

    @Test
    void handle_autocompleteModule_suggestionsProvided() throws ParseException {
        String input = "someCommand m/";
        assertEquals(autocompleteHandler.handle(input), "someCommand m/" + AMY.getModCode().toString());
        assertEquals(autocompleteHandler.handle(input), "someCommand m/" + BOB.getModCode().toString());
    }

    @Test
    void handle_autocompleteTutorial_suggestionsProvided() throws ParseException {
        String input = "someCommand tn/";
        assertEquals(autocompleteHandler.handle(input), "someCommand tn/" + AMY.getTutName().toString());
        assertEquals(autocompleteHandler.handle(input), "someCommand tn/" + BOB.getTutName().toString());
    }

    @Test
    void handle_autocompleteEmail_suggestionsProvided() throws ParseException {
        String input = "someCommand e/" + AMY.getEmail().toString().substring(0, 5);
        assertEquals(autocompleteHandler.handle(input), "someCommand e/" + AMY.getEmail().toString());
    }

    @Test
    void handle_autocompleteCommand_suggestionsProvided() throws ParseException {
        String input = "addS";
        assertEquals(autocompleteHandler.handle(input), "addStudent");
    }

    @Test
    void handle_autocompleteInvalidCommand_noSuggestionsProvided() {
        String input = "someInvalidCommandWord";
        assertThrows(IndexOutOfBoundsException.class, () -> autocompleteHandler.handle(input));
    }

    @Test
    void handle_autocompleteInvalidPrefix_unableToDetectField() {
        String input = "someCommand abc/";
        assertThrows(ParseException.class, () -> autocompleteHandler.handle(input));
    }

    //CHECKSTYLE:OFF: VisibilityModifier
    private class ModelStubAutocompleteHandler extends ModelStub {
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();
        final ArrayList<Student> students = new ArrayList<>();

        PartialInput suggestedCompletions;
        boolean isInputChanged = true;

        private List<Command> suggestedCommands = new ArrayList<>();

        @Override
        public void addModule(Module module) {
            modules.add(module);
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            tutorials.add(tutorial);
        }

        @Override
        public void addStudent(Student student) {
            students.add(student);
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            ObservableList<Module> list = FXCollections.observableArrayList();
            list.addAll(modules);
            return list;
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            ObservableList<Tutorial> list = FXCollections.observableArrayList();
            list.addAll(tutorials);
            return list;
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            ObservableList<Student> list = FXCollections.observableArrayList();
            list.addAll(students);
            return list;
        }

        @Override
        public void storeSuggestedCompletions(PartialInput suggestedCompletions) {
            this.suggestedCompletions = suggestedCompletions;
        }

        @Override
        public PartialInput getSuggestedCompletions() {
            return suggestedCompletions;
        }

        @Override
        public void deleteSuggestedCompletions() {
            suggestedCompletions = null;
        }

        @Override
        public boolean hasSuggestedCompletions() {
            return suggestedCompletions == null;
        }

        @Override
        public void setInputChangedToTrue() {
            isInputChanged = true;
        }

        @Override
        public void setInputChangedToFalse() {
            isInputChanged = false;
        }

        @Override
        public boolean hasInputChanged() {
            return isInputChanged;
        }

    }
}
