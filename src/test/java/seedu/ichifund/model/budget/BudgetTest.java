package seedu.ichifund.model.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_ANIME;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_FOOD;

import org.junit.jupiter.api.Test;

import seedu.ichifund.testutil.BudgetBuilder;

public class BudgetTest {

    @Test
    public void isSameBudget() {
        // same object -> returns true
        assertTrue(BUDGET_ANIME.isSameBudget(BUDGET_ANIME));

        // null -> returns false
        assertFalse(BUDGET_ANIME.isSameBudget(null));

        // different description -> returns false
        Budget editedAnime = new BudgetBuilder(BUDGET_ANIME)
                .withDescription(BUDGET_FOOD.getDescription().toString()).build();
        assertFalse(BUDGET_ANIME.isSameBudget(editedAnime));


        // same description, same month, same year, same category, different amount -> returns true
        editedAnime = new BudgetBuilder(BUDGET_ANIME).withAmount(BUDGET_FOOD.getAmount().toString()).build();
        assertTrue(BUDGET_ANIME.isSameBudget(editedAnime));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Budget animeCopy = new BudgetBuilder(BUDGET_ANIME).build();
        assertTrue(BUDGET_ANIME.equals(animeCopy));

        // same object -> returns true
        assertTrue(BUDGET_ANIME.equals(BUDGET_ANIME));

        // null -> returns false
        assertFalse(BUDGET_ANIME.equals(null));

        // different type -> returns false
        assertFalse(BUDGET_ANIME.equals(5));

        // different budget -> returns false
        assertFalse(BUDGET_ANIME.equals(BUDGET_FOOD));
    }
}
