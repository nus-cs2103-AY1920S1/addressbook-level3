package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.testutil.AnswerableBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newAnswerable_success() {
        Answerable validAnswerable = new AnswerableBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAnswerable(validAnswerable);

        assertCommandSuccess(new AddCommand(validAnswerable), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validAnswerable), expectedModel);
    }

    @Test
    public void execute_duplicateAnswerable_throwsCommandException() {
        Answerable answerableInList = model.getAddressBook().getAnswerableList().get(0);
        assertCommandFailure(new AddCommand(answerableInList), model, AddCommand.MESSAGE_DUPLICATE_ANSWERABLE);
    }

}
