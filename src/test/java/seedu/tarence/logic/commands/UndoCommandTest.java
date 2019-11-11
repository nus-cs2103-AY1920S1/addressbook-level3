package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.JsonApplicationStorage;
import seedu.tarence.storage.JsonStateStorage;
import seedu.tarence.storage.JsonUserPrefsStorage;
import seedu.tarence.storage.Storage;
import seedu.tarence.storage.StorageManager;

public class UndoCommandTest {
    public static final String VALID_MOD_CODE = "GER1000";
    public static final String VALID_TUT_NAME = "T01";
    public static final String VALID_START_DATE = "01-01-2020 0000";
    public static final String VALID_END_DATE = "31-12-2020 2359";
    public static final String VALID_SEM_START_DATE = "01-08-2020";
    public static final String VALID_SEM_START_DATE_TWO = "01-09-2020";
    public static final String VALID_EVENT_NAME = "Consultation";

    private static Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
    private static Tutorial validTutorial;
    private static Module validModule;
    private static Event validEvent;
    private static Storage storage;
    private static SimpleDateFormat eventDateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);
    private static SimpleDateFormat semDateFormatter = new SimpleDateFormat(SetSemStartCommand.DATE_FORMAT);

    @BeforeAll
    static void setup() throws ParseException {
        validTutorial = new TutorialBuilder().withTutName(VALID_TUT_NAME).withModCode(VALID_MOD_CODE).build();
        validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        validModule.addTutorial(validTutorial);
        model.addModule(validModule);
        model.addTutorial(validTutorial);

        validEvent = new Event(VALID_EVENT_NAME,
                eventDateFormatter.parse(VALID_START_DATE), eventDateFormatter.parse(VALID_END_DATE));
        validTutorial.addEvent(validEvent);
        Path filePath = Paths.get("data");
        storage = new StorageManager(new JsonApplicationStorage(filePath), new JsonUserPrefsStorage(filePath),
                new JsonStateStorage("data", "states"));
    }

    @Test
    public void execute_invalidStatedToUno_throwCommandException() {
        UndoCommand undoCommand = new UndoCommand(1);
        assertThrows(CommandException.class, () -> undoCommand.execute(model, storage));
    }

    @Test
    public void isMatchingCommandWord_validCommandWord_returnsTrue() {
        String userCommand = "undo";
        assertTrue(UndoCommand.isMatchingCommandWord(userCommand));
    }

    @Test
    public void isMatchingCommandWord_invalidCommandWord_returnsFalse() {
        String userCommand = "invalidCommand";
        assertFalse(UndoCommand.isMatchingCommandWord(userCommand));
    }



}
