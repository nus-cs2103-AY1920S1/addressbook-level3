package seedu.tarence.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
        String incompleteField = "A";
        String input = "someCommand n/" + incompleteField;
        assertEquals(autocompleteHandler.handle(input),
                StringUtils.removeStart(AMY.getName().toString().toLowerCase(), incompleteField.toLowerCase()));
        assertEquals(autocompleteHandler.getNextSuggestion(),
                StringUtils.removeStart(ALICE.getName().toString().toLowerCase(), incompleteField.toLowerCase()));
    }

    @Test
    void handle_autocompleteInvalidName_noSuggestionsFound() throws ParseException {
        String input = "someCommand n/C";
        assertEquals(autocompleteHandler.handle(input), "");
    }

    @Test
    void handle_autocompleteModule_suggestionsProvided() throws ParseException {
        String input = "someCommand m/";
        assertEquals(autocompleteHandler.handle(input), AMY.getModCode().toString().toLowerCase());
        assertEquals(autocompleteHandler.getNextSuggestion(), BOB.getModCode().toString().toLowerCase());
    }

    @Test
    void handle_autocompleteTutorial_suggestionsProvided() throws ParseException {
        String input = "someCommand tn/";
        assertEquals(autocompleteHandler.handle(input), AMY.getTutName().toString().toLowerCase());
        assertEquals(autocompleteHandler.getNextSuggestion(), BOB.getTutName().toString().toLowerCase());
    }

    @Test
    void handle_autocompleteEmail_suggestionsProvided() throws ParseException {
        String incompleteField = AMY.getEmail().toString().substring(0, 5);
        String input = "someCommand e/" + incompleteField;
        assertEquals(autocompleteHandler.handle(input),
                StringUtils.removeStart(AMY.getEmail().toString().toLowerCase(),
                        AMY.getEmail().toString().substring(0, 5).toLowerCase()));
    }

    @Test
    void handle_autocompleteCommand_suggestionsProvided() throws ParseException {
        assertEquals(autocompleteHandler.handle("addSt"), "udent");
    }

    @Test
    void handle_autocompleteInvalidCommand_noSuggestionsProvided() throws ParseException {
        assertEquals(autocompleteHandler.handle("someInvalidCommandWord"), "");
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
