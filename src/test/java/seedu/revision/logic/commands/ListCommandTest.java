package seedu.revision.logic.commands;

import static seedu.revision.testutil.TypicalMcqs.getTypicalRevisionTool;

import org.junit.jupiter.api.BeforeEach;

import seedu.revision.model.History;
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

    //TODO: Make into actual stub
    private static CategoryPredicate categoryPredicateStub =
            new CategoryPredicate(new Category("CATEGORY"));
    private static DifficultyPredicate difficultyPredicateStub = new DifficultyPredicate(new Difficulty("1"));
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRevisionTool(), new UserPrefs(), new History());
        expectedModel = new ModelManager(model.getRevisionTool(), new UserPrefs(), new History());
    }

    /*
    @Test
    public void execute_listIsNotFiltered_showsSameList() throws ParseException {
        assertCommandSuccess(new ListCommand(categoryPredicateStub, difficultyPredicateStub),
                model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    */

    /*
    @Test
    public void execute_listIsFiltered_showsEverything() throws ParseException {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);
        assertCommandSuccess(new ListCommand(categoryPredicateStub, difficultyPredicateStub),
                model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    */
}
