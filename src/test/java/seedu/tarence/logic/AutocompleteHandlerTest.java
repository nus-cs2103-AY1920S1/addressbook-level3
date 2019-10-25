package seedu.tarence.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
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
        Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
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
}
