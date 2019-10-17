package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAnswerableAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.address.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.predicates.CategoryPredicate;
import seedu.address.model.answerable.predicates.DifficultyPredicate;
import seedu.address.model.category.Category;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    //TODO: Make into actual stub
    private static CategoryPredicate CATEGORY_PREDICATE_STUB = new CategoryPredicate(new Category("CATEGORY"));
    private static DifficultyPredicate DIFFICULTY_PREDICATE_STUB = new DifficultyPredicate(new Difficulty("1"));

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(CATEGORY_PREDICATE_STUB, DIFFICULTY_PREDICATE_STUB), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);
        assertCommandSuccess(new ListCommand(CATEGORY_PREDICATE_STUB, DIFFICULTY_PREDICATE_STUB), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
