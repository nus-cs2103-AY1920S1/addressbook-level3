package seedu.address.logic.commands.findcommand;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class FindTeamCommandTest {
    private Model modelManager = mock(ModelManager.class);

    @Test
    public void execute_validParameters_success() throws AlfredException {
        when(modelManager.findTeam(any())).thenReturn(
                new ArrayList<>());
        doNothing().when(modelManager).updateHistory(any());
        FindTeamCommand command = new FindTeamCommand(
                Optional.of("teamname"),
                Optional.empty()
        );
        String expectedMessage = FindTeamCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command,
                this.modelManager, expectedMessage, this.modelManager);
    }

    @Test
    public void execute_multipleValidParameters_success() throws AlfredException {
        when(modelManager.findTeam(any())).thenReturn(
               new ArrayList<>());
        doNothing().when(modelManager).updateHistory(any());
        FindTeamCommand command = new FindTeamCommand(
                Optional.of("P"),
                Optional.of("projectname")
        );
        String expectedMessage = FindTeamCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command,
                this.modelManager, expectedMessage, this.modelManager);
    }
}
