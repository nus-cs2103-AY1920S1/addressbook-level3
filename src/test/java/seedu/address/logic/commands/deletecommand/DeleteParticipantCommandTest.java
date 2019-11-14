package seedu.address.logic.commands.deletecommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PARTICIPANT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.ParticipantBuilder;

// TODO: test deleting from Team (later)
public class DeleteParticipantCommandTest {

    @Test
    public void execute_validIndexParticipantList_success() throws AlfredException {
        Model model = new ModelManagerStub();
        Participant participantToDelete = new ParticipantBuilder().build();
        model.addParticipant(participantToDelete);
        DeleteParticipantCommand deleteCommand = new DeleteParticipantCommand(participantToDelete.getId());

        String expectedMessage = String.format(
                DeleteParticipantCommand.MESSAGE_DELETE_PARTICIPANT_SUCCESS,
                participantToDelete
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addParticipant(participantToDelete);
        expectedModel.deleteParticipant(participantToDelete.getId());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexParticipantList_throwsCommandException() {
        Model model = new ModelManagerStub(); // empty model
        Id outOfBoundId = ID_FIRST_PARTICIPANT;
        DeleteParticipantCommand deleteCommand = new DeleteParticipantCommand(outOfBoundId);

        assertCommandFailure(
                deleteCommand,
                model,
                DeleteParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX
        );
    }

}
