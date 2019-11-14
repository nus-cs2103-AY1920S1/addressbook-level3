package organice.logic.commands;

import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_IRENE_DONOR;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import organice.model.AddressBook;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.model.person.Nric;
import organice.model.person.TaskList;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code MatchCommand}.
 */
public class ProcessingCommandIntegrationTest {

    private static final String VALID_NRIC_NOT_IN_ORGANICE = "S8266747D";
    private static final String INVALID_NRIC = "G123A";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_patientDonorMatch_isProcessed() {
        //create a new AddressBook
        AddressBook ab = new AddressBook();
        ab.addPerson(new PatientBuilder(PATIENT_IRENE).build());
        ab.addPerson(new DonorBuilder(DONOR_IRENE_DONOR).build());
        Model initialModel = new ModelManager(ab, new UserPrefs());

        ProcessingCommand processingCommand = new ProcessingCommand(VALID_NRIC_DONOR_IRENE_DONOR,
                VALID_NRIC_PATIENT_IRENE);
        TaskList taskList = new TaskList("").defaultList();
        String emptyTaskList = taskList.display();
        assertCommandSuccess(processingCommand, initialModel, emptyTaskList, initialModel);
    }

    @Test
    public void execute_patientNotInOrganice_returnNotProcessedMessage() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ProcessingCommand processingCommand = new ProcessingCommand(VALID_NRIC_DONOR_IRENE_DONOR,
                VALID_NRIC_NOT_IN_ORGANICE);
        assertCommandSuccess(processingCommand, model, ProcessingCommand.MESSAGE_NOT_PROCESSED, expectedModel);
    }

    @Test
    public void execute_notMatchInOrganice_returnNotProcessedMessage() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ProcessingCommand processingCommand = new ProcessingCommand(VALID_NRIC_DONOR_JOHN,
                VALID_NRIC_PATIENT_IRENE);
        assertCommandSuccess(processingCommand, model, ProcessingCommand.MESSAGE_NOT_PROCESSED, expectedModel);
    }

    @Test
    public void execute_invalidNric_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Nric.MESSAGE_CONSTRAINTS, () -> new ProcessingCommand(INVALID_NRIC,
                VALID_NRIC_PATIENT_IRENE).execute(model));
    }
}
