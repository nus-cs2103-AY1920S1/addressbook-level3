package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.History;
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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new History());
    }

    @Test
    public void execute_newAnswerable_success() throws ParseException {
        Answerable validAnswerable = new AnswerableBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new History());
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
