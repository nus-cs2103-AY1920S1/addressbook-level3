package seedu.address.logic.quiz.commands;

import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.quiz.commands.CommandTestUtil.showQuestionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalQuestion.getTypicalAddressQuizBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.model.quiz.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelQuizManager(getTypicalAddressQuizBook(), new UserPrefs());
        expectedModel = new ModelQuizManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showQuestionAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
