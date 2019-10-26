package organice.logic.commands;

import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import organice.logic.parser.MatchCommandParser;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code MatchCommand}.
 */
public class MatchCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPerson(PATIENT_IRENE);
        model.addPerson(DONOR_IRENE_DONOR);
    }

    @Test
    public void execute_matchNric_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(MatchCommand.MESSAGE_SUCCESS);
        expectedCommandResult.setMatch(true);

        assertCommandSuccess(new MatchCommand(VALID_NRIC_PATIENT_IRENE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_matchAll_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(MatchCommand.MESSAGE_SUCCESS);
        expectedCommandResult.setMatch(true);

        assertCommandSuccess(new MatchCommand(MatchCommandParser.ALL), model, expectedCommandResult, expectedModel);
    }
}
