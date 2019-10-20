package seedu.tarence.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_BOB;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.TypicalStudents;

public class PartialInputParserTest {

    private static Model model;

    private static final Module MODULE_AMY = new ModuleBuilder().withModCode(VALID_MODULE_AMY).build();
    private static final Module MODULE_BOB = new ModuleBuilder().withModCode(VALID_MODULE_BOB).build();

    private static final Tutorial TUTORIAL_AMY = new TutorialBuilder().withModCode(VALID_MODULE_AMY)
            .withTutName(VALID_TUTORIAL_NAME_AMY).build();
    private static final Tutorial TUTORIAL_BOB = new TutorialBuilder().withModCode(VALID_MODULE_BOB)
            .withTutName(VALID_TUTORIAL_NAME_BOB).build();

    private static final Student AMY = TypicalStudents.AMY;
    private static final Student BOB = TypicalStudents.BOB;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalApplication(), new UserPrefs());
        model.addModule(MODULE_AMY);
        model.addModule(MODULE_BOB);
        model.addTutorial(TUTORIAL_AMY);
        model.addTutorial(TUTORIAL_BOB);
        model.addTutorialToModule(TUTORIAL_AMY);
        model.addTutorialToModule(TUTORIAL_BOB);
        model.addStudent(AMY);
        model.addStudent(BOB);
        model.addStudentToTutorial(AMY);
        model.addStudentToTutorial(BOB);
    }

    @Test
    public void autocomplete_singleFieldMatchingNameFound_completionSuggested() throws ParseException {
        String partialInputString = "someCommand n/Am";
        PartialInput actualPartialInput = PartialInputParser.parse(partialInputString, model);

        List<String> expectedCompletions = new ArrayList<>(Collections.singletonList(AMY.getName().toString()));

        PartialInput expectedPartialInput = new PartialInput(partialInputString,
                "Am", expectedCompletions);

        assertEquals(expectedPartialInput, actualPartialInput);
    }

    @Test
    public void autocomplete_lastFieldMatchingNameFound_completionSuggested() throws ParseException {
        String partialInputString = "someCommand m/CS9999 n/Bob n/Am";
        PartialInput actualPartialInput = PartialInputParser.parse(partialInputString, model);

        List<String> expectedCompletions = new ArrayList<>(Collections.singletonList(AMY.getName().toString()));

        PartialInput expectedPartialInput = new PartialInput(partialInputString,
                "Am", expectedCompletions);

        assertEquals(expectedPartialInput, actualPartialInput);
    }

    @Test
    public void autocomplete_multipleNameSuggestions_completionSuggested() throws ParseException {
        String partialInputString = "someCommand m/OW1010 tn/Heroes n/";
        PartialInput actualPartialInput = PartialInputParser.parse(partialInputString, model);

        List<String> expectedCompletions = new ArrayList<>(Arrays.asList(
                AMY.getName().toString(),
                BOB.getName().toString()));

        PartialInput expectedPartialInput = new PartialInput(partialInputString,
                "", expectedCompletions);

        assertEquals(expectedPartialInput, actualPartialInput);
    }

    @Test
    public void autocomplete_noNameSuggestionsFound_emptySuggestedCompletions() throws ParseException {
        String partialInputString = "someCommand m/OW1010 tn/Heroes n/Cathy";
        PartialInput actualPartialInput = PartialInputParser.parse(partialInputString, model);

        List<String> expectedCompletions = new ArrayList<>();

        PartialInput expectedPartialInput = new PartialInput(partialInputString,
                "Cathy", expectedCompletions);

        assertEquals(expectedPartialInput, actualPartialInput);
    }

    @Test
    public void autocomplete_multipleTutNameSuggestions_completionSuggested() throws ParseException {
        String partialInputString = "someCommand tn/";
        PartialInput actualPartialInput = PartialInputParser.parse(partialInputString, model);

        List<String> expectedCompletions = new ArrayList<>(Arrays.asList(
                AMY.getTutName().toString(),
                BOB.getTutName().toString()));

        PartialInput expectedPartialInput = new PartialInput(partialInputString,
                "", expectedCompletions);

        assertEquals(expectedPartialInput, actualPartialInput);
    }

}
