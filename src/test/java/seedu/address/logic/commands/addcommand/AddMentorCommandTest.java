package seedu.address.logic.commands.addcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_MENTOR;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Mentor;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.Assert;
import seedu.address.testutil.MentorBuilder;

public class AddMentorCommandTest {

    @Test
    public void constructor_nullMentor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMentorCommand(null));
    }

    @Test
    public void execute_mentorAcceptedByModel_addSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Mentor validMentor = new MentorBuilder().build();

        CommandResult commandResult = new AddMentorCommand(validMentor).execute(modelStub);

        assertEquals(String.format(AddMentorCommand.MESSAGE_SUCCESS, validMentor),
                commandResult.getFeedbackToUser());
        assertEquals(validMentor, modelStub.getMentor(ID_FIRST_MENTOR));
    }

    @Test
    public void execute_duplicateMentor_throwsCommandException() throws AlfredException {
        Mentor validMentor = new MentorBuilder().build();
        AddMentorCommand addMentorCommand = new AddMentorCommand(validMentor);
        ModelManagerStub modelStub = new ModelManagerStub();
        addMentorCommand.execute(modelStub);

        Assert.assertThrows(CommandException.class,
                AddMentorCommand.MESSAGE_DUPLICATE_MENTOR, () -> addMentorCommand.execute(modelStub));
    }

}
