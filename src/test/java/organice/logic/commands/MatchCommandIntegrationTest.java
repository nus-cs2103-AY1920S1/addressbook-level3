package organice.logic.commands;

import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
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
}
