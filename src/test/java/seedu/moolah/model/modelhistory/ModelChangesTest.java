package seedu.moolah.model.modelhistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.UserPrefs;

public class ModelChangesTest {

    @Test
    public void copyConstructor() {
        ModelChanges changes = new ModelChanges("test copy")
                .setMooLah(new MooLah())
                .setUserPrefs(new UserPrefs())
                .setExpensePredicate(Model.PREDICATE_SHOW_ALL_EXPENSES)
                .setEventPredicate(Model.PREDICATE_SHOW_ALL_EVENTS)
                .setBudgetPredicate(Model.PREDICATE_SHOW_ALL_BUDGETS);

        assertEquals(changes, changes.copy());
    }

    @Test
    public void compareModels() {
        String changeMessage = "test compare";
        Model base = new ModelManager();
        Model other = new ModelManager();
        ModelChanges changes;

        // Does not include anything
        changes = ModelChanges.compareModels(changeMessage, base, other);
        assertEquals(changes, new ModelChanges(changeMessage));

        // Includes mooLah
        other = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        changes = ModelChanges.compareModels(changeMessage, base, other);
        assertEquals(changes, new ModelChanges(changeMessage).setMooLah(base.getMooLah()));

        // Includes userPrefs
        other = new ModelManager();
        other.setMooLahFilePath(Paths.get("dummyFilepath"));
        changes = ModelChanges.compareModels(changeMessage, base, other);
        assertEquals(changes, new ModelChanges(changeMessage).setUserPrefs(base.getUserPrefs()));

        // Includes expensePredicate
        other = new ModelManager();
        other.updateFilteredExpenseList(expense -> false);
        changes = ModelChanges.compareModels(changeMessage, base, other);
        assertEquals(changes, new ModelChanges(changeMessage).setExpensePredicate(base.getFilteredExpensePredicate()));

        // Includes eventPredicate
        other = new ModelManager();
        other.updateFilteredEventList(event -> false);
        changes = ModelChanges.compareModels(changeMessage, base, other);
        assertEquals(changes, new ModelChanges(changeMessage).setEventPredicate(base.getFilteredEventPredicate()));

        // Includes budgetPredicate
        other = new ModelManager();
        other.updateFilteredBudgetList(budget -> false);
        changes = ModelChanges.compareModels(changeMessage, base, other);
        assertEquals(changes, new ModelChanges(changeMessage).setBudgetPredicate(base.getFilteredBudgetPredicate()));
    }

    @Test
    public void revertChanges() {
        Model model = new ModelManager();

        String changeMessage = "test revert changes";
        ModelChanges changes = new ModelChanges(changeMessage)
                .setMooLah(new MooLah())
                .setUserPrefs(new UserPrefs())
                .setExpensePredicate(Model.PREDICATE_SHOW_ALL_EXPENSES)
                .setEventPredicate(Model.PREDICATE_SHOW_ALL_EVENTS)
                .setBudgetPredicate(Model.PREDICATE_SHOW_ALL_BUDGETS);
        ModelChanges expected = new ModelChanges(changeMessage)
                .setMooLah(model.getMooLah())
                .setUserPrefs(model.getUserPrefs())
                .setExpensePredicate(model.getFilteredExpensePredicate())
                .setEventPredicate(model.getFilteredEventPredicate())
                .setBudgetPredicate(model.getFilteredBudgetPredicate());
        ModelChanges actual = changes.revertChanges(model);

        assertEquals(expected, actual);
    }

    @Test
    public void toString_success() {
        ModelChanges changes = new ModelChanges("test toString");
        assertEquals("changed:", changes.toString());

        changes.setMooLah(new MooLah());
        assertEquals("changed: mooLah", changes.toString());

        changes.setUserPrefs(new UserPrefs());
        assertEquals("changed: mooLah userPrefs", changes.toString());

        changes.setExpensePredicate(expense -> true);
        assertEquals("changed: mooLah userPrefs expensePredicate", changes.toString());

        changes.setEventPredicate(event -> true);
        assertEquals("changed: mooLah userPrefs expensePredicate eventPredicate", changes.toString());

        changes.setBudgetPredicate(budget -> true);
        assertEquals("changed: mooLah userPrefs expensePredicate eventPredicate budgetPredicate", changes.toString());
    }

    @Test
    public void equals() {
        String changeMessage = "test equals";
        ModelChanges changes = new ModelChanges(changeMessage);
        ModelChanges other;

        // To same object
        assertEquals(changes, changes);

        // To objects of different types
        assertFalse(changes.equals(1));

        // To objects with different change message
        other = new ModelChanges("other");
        assertNotEquals(changes, other);

        // To objects with null vs non-null mooLah
        other = new ModelChanges(changeMessage).setMooLah(new MooLah());
        assertNotEquals(changes, other);

        // To objects with different mooLah
        changes = new ModelChanges(changeMessage).setMooLah(new MooLah());
        other = new ModelChanges(changeMessage).setMooLah(getTypicalMooLah());
        assertNotEquals(changes, other);

        // To objects with null vs non-null userPrefs
        changes = new ModelChanges(changeMessage);
        other = new ModelChanges(changeMessage).setUserPrefs(new UserPrefs());
        assertNotEquals(changes, other);

        // To objects with different userPrefs
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMooLahFilePath(Paths.get("dummyFilepath"));
        changes = new ModelChanges(changeMessage).setUserPrefs(new UserPrefs());
        other = new ModelChanges(changeMessage).setUserPrefs(userPrefs);
        assertNotEquals(changes, other);


        // To objects with different expensePredicate
        changes = new ModelChanges(changeMessage);
        other = new ModelChanges(changeMessage).setExpensePredicate(Model.PREDICATE_SHOW_ALL_EXPENSES);
        assertNotEquals(changes, other);

        // To objects with different eventPredicate
        other = new ModelChanges(changeMessage).setEventPredicate(Model.PREDICATE_SHOW_ALL_EVENTS);
        assertNotEquals(changes, other);

        // To objects with different budgetPredicate
        other = new ModelChanges(changeMessage).setBudgetPredicate(Model.PREDICATE_SHOW_ALL_BUDGETS);
        assertNotEquals(changes, other);

        // To objects with all equal values
        changes = new ModelChanges(changeMessage)
                .setMooLah(new MooLah())
                .setUserPrefs(new UserPrefs())
                .setExpensePredicate(Model.PREDICATE_SHOW_ALL_EXPENSES)
                .setEventPredicate(Model.PREDICATE_SHOW_ALL_EVENTS)
                .setBudgetPredicate(Model.PREDICATE_SHOW_ALL_BUDGETS);
        other = new ModelChanges(changeMessage)
                .setMooLah(new MooLah())
                .setUserPrefs(new UserPrefs())
                .setExpensePredicate(Model.PREDICATE_SHOW_ALL_EXPENSES)
                .setEventPredicate(Model.PREDICATE_SHOW_ALL_EVENTS)
                .setBudgetPredicate(Model.PREDICATE_SHOW_ALL_BUDGETS);
        assertEquals(changes, other);
    }

}
