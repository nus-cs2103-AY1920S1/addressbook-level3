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
import seedu.address.testutil.TypicalMentors;

public class FindMentorCommandTest {
    private Model modelManager = mock(ModelManager.class);

    @Test
    public void execute_validParameters_success() throws AlfredException {
        when(modelManager.findMentor(any())).thenReturn(
                TypicalMentors.getTypicalMentors());
        doNothing().when(modelManager).updateHistory(any());
        FindMentorCommand command = new FindMentorCommand(
                FindCommandUtilEnum.AND,
                Optional.empty(),
                Optional.of("email"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        String expectedMessage = FindMentorCommand.MESSAGE_SUCCESS + "n/" + "" + " " + "e/"
                + "email" + " " + "p/" + "" + " "
                + "o/" + "" + "\n"
                + "Excluded the following: \n"
                + "n/" + "" + " " + "e/" + "" + " " + "p/" + "" + " "
                + "o/" + "" + "\n";

        assertCommandSuccess(command,
                this.modelManager, expectedMessage, this.modelManager);
    }

    @Test
    public void execute_multipleValidParameters_success() throws AlfredException {
        when(modelManager.findMentor(any())).thenReturn(
                TypicalMentors.getTypicalMentors());
        doNothing().when(modelManager).updateHistory(any());
        FindMentorCommand command = new FindMentorCommand(
                FindCommandUtilEnum.AND,
                Optional.of("P"),
                Optional.of("example"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        String expectedMessage = FindMentorCommand.MESSAGE_SUCCESS + "n/" + "P" + " " + "e/"
                + "example" + " " + "p/" + "" + " "
                + "o/" + "" + "\n"
                + "Excluded the following: \n"
                + "n/" + "" + " " + "e/" + "" + " " + "p/" + "" + " "
                + "o/" + "" + "\n";

        assertCommandSuccess(command,
                this.modelManager, expectedMessage, this.modelManager);
    }
}
