package seedu.address.logic.commands.deletecommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_MENTOR;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.MentorBuilder;

public class DeleteMentorCommandTest {

    @Test
    public void execute_validIndexMentorList_success() throws AlfredException {
        Model model = new ModelManagerStub();
        Mentor mentorToDelete = new MentorBuilder().build();
        model.addMentor(mentorToDelete);
        DeleteMentorCommand deleteCommand = new DeleteMentorCommand(mentorToDelete.getId());

        String expectedMessage = String.format(
                DeleteMentorCommand.MESSAGE_DELETE_MENTOR_SUCCESS,
                mentorToDelete
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addMentor(mentorToDelete);
        expectedModel.deleteMentor(mentorToDelete.getId());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexMentorList_throwsCommandException() {
        Model model = new ModelManagerStub(); // empty model
        Id outOfBoundId = ID_FIRST_MENTOR;
        DeleteMentorCommand deleteCommand = new DeleteMentorCommand(outOfBoundId);

        assertCommandFailure(
                deleteCommand,
                model,
                DeleteMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX
        );
    }

}
