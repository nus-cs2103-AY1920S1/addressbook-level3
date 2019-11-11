package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static organice.logic.commands.CommandTestUtil.BLOOD_TYPE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_IRENE_DONOR;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import organice.logic.parser.EditCommandParser;
import organice.logic.parser.MatchCommandParser;
import organice.model.AddressBook;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code MatchCommand}.
 */
public class MatchCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_matchNric_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(
                String.format(MatchCommand.MESSAGE_SUCCESS_MATCH_PATIENT, 1, VALID_NAME_PATIENT_IRENE,
                        VALID_NRIC_PATIENT_IRENE));

        assertCommandSuccess(new MatchCommand(VALID_NRIC_PATIENT_IRENE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_matchAll_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(MatchCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new MatchCommand(MatchCommandParser.ALL), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_matchNricAfterEdit_matchResultsUpdated() throws Exception {
        Model initialModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        new MatchCommand(VALID_NRIC_PATIENT_IRENE).execute(initialModel);
        EditCommand editPatientIrene = new EditCommandParser().parse(VALID_NRIC_PATIENT_IRENE
                + BLOOD_TYPE_DESC_PATIENT_BOB);
        editPatientIrene.execute(initialModel);

        //execute matching again
        new MatchCommand(VALID_NRIC_PATIENT_IRENE).execute(initialModel);
        //donor does not match patient anymore
        assertEquals(0, initialModel.numberOfMatches());
    }

    @Test
    public void execute_matchNricAfterProcessing_notMatch() {
        //create a new AddressBook
        AddressBook ab = new AddressBook();
        ab.addPerson(new PatientBuilder(PATIENT_IRENE).build());
        ab.addPerson(new DonorBuilder(DONOR_IRENE_DONOR).build());
        Model initialModel = new ModelManager(ab, new UserPrefs());

        new MatchCommand(VALID_NRIC_PATIENT_IRENE).execute(initialModel);

        assertEquals(1, initialModel.numberOfMatches());

        ProcessingCommand processIreneMatch = new ProcessingCommand(VALID_NRIC_PATIENT_IRENE,
                VALID_NRIC_DONOR_IRENE_DONOR);
        processIreneMatch.execute(initialModel);

        DoneCommand processIreneMatchFail = new DoneCommand(VALID_NRIC_PATIENT_IRENE, VALID_NRIC_DONOR_IRENE_DONOR,
                "fail");
        processIreneMatchFail.execute(initialModel);

        //match after updating cross matching results
        new MatchCommand(VALID_NRIC_PATIENT_IRENE).execute(initialModel);
        assertEquals(0, initialModel.numberOfMatches());
    }
}
