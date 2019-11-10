package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.logic.commands.SetSemStartCommand.MESSAGE_PREVIOUS_SEM_START_HAS_BEEN_CHANGED;
import static seedu.tarence.logic.commands.SetSemStartCommand.MESSAGE_SET_SEM_START_SUCCESS;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

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

public class SetSemStartCommandIntegrationTest {

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
    }

    @Test
    void execute_validDates_dateSetSuccessfully() throws ParseException, CommandException {
        SetSemStartCommand setSemStartCommand = new SetSemStartCommand(semDateFormatter.parse(VALID_SEM_START_DATE));
        String expectedMessage = String.format(MESSAGE_SET_SEM_START_SUCCESS + "\n",
                VALID_SEM_START_DATE);

        assertEquals(new CommandResult(expectedMessage), setSemStartCommand.execute(model));

        setSemStartCommand = new SetSemStartCommand(semDateFormatter.parse(VALID_SEM_START_DATE_TWO));
        String previousDateMessage = String.format(MESSAGE_PREVIOUS_SEM_START_HAS_BEEN_CHANGED + "\n",
                VALID_SEM_START_DATE);
        String newDateMessage = String.format(MESSAGE_SET_SEM_START_SUCCESS + "\n",
                VALID_SEM_START_DATE_TWO);
        assertEquals(new CommandResult(previousDateMessage + newDateMessage),
                setSemStartCommand.execute(model));
    }
}
