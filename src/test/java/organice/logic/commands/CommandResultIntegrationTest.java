package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import organice.logic.parser.MatchCommandParser;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;

public class CommandResultIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void isMatch_matchNricCommandResult_returnsTrue() {
        CommandResult matchCommandResult = (new MatchCommand(VALID_NRIC_PATIENT_IRENE)).execute(model);
        assertTrue(matchCommandResult.isMatch());
    }

    @Test
    public void isMatch_matchAllCommandResult_returnsTrue() {
        CommandResult matchCommandResult = (new MatchCommand(MatchCommandParser.ALL)).execute(model);
        assertTrue(matchCommandResult.isMatch());
    }
}
