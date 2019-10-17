package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.CommandTestUtil.showAnswerableAtIndex;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.revision.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.category.Category;

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
