package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.address.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.address.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.address.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.IntervieweeList;
import seedu.address.model.InterviewerList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new IntervieweeList(), new InterviewerList(), new UserPrefs(), new LinkedList<>());
    }

    @Test
    public void execute_newInterviewee_success() {
        Interviewee validInterviewee = ALICE_INTERVIEWEE;

        Model expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel.addInterviewee(validInterviewee);

        assertCommandSuccess(new AddIntervieweeCommand(validInterviewee), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validInterviewee), expectedModel);
    }

    @Test
    public void execute_newInterviewer_success() {
        Interviewer validInterviewer = ALICE_INTERVIEWER;

        Model expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel.addInterviewer(validInterviewer);

        assertCommandSuccess(new AddInterviewerCommand(validInterviewer), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validInterviewer), expectedModel);
    }

    @Test
    public void execute_duplicateInterviewee_throwsCommandException() {
        model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewee intervieweeInList = model.getInterviewee(ALICE_INTERVIEWEE.getName().fullName);
        assertCommandFailure(new AddIntervieweeCommand(intervieweeInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
