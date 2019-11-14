package seedu.address.logic.commands.findcommand;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.parser.findcommandparser.FindCommandUtilEnum;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalParticipants;

public class FindParticipantCommandTest {
    private Model modelManager = mock(ModelManager.class);

    @Test
    public void execute_validParameters_success() throws AlfredException {
        when(modelManager.findParticipant(any())).thenReturn(
                 TypicalParticipants.getTypicalParticipants());
        doNothing().when(modelManager).updateHistory(any());
        FindParticipantCommand command = new FindParticipantCommand(
                FindCommandUtilEnum.AND,
                Optional.of("P"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        String expectedMessage = FindParticipantCommand.MESSAGE_SUCCESS + "n/" + "P" + " " + "e/"
                + "" + " " + "p/" + "" + "\n"
                + "Excluded the following: \n"
                + "n/" + "" + " " + "e/" + "" + " " + "p/" + "" + "\n";

        assertCommandSuccess(command,
                this.modelManager, expectedMessage, this.modelManager);
    }

    @Test
    public void execute_multipleValidParameters_success() throws AlfredException {
        when(modelManager.findParticipant(any())).thenReturn(
                TypicalParticipants.getTypicalParticipants());
        doNothing().when(modelManager).updateHistory(any());
        FindParticipantCommand command = new FindParticipantCommand(
                FindCommandUtilEnum.AND,
                Optional.of("P"),
                Optional.of("example"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        String expectedMessage = FindParticipantCommand.MESSAGE_SUCCESS + "n/" + "P" + " " + "e/"
                + "example" + " " + "p/" + "" + "\n"
                + "Excluded the following: \n"
                + "n/" + "" + " " + "e/" + "" + " " + "p/" + "" + "\n";

        assertCommandSuccess(command,
                this.modelManager, expectedMessage, this.modelManager);
    }
}
