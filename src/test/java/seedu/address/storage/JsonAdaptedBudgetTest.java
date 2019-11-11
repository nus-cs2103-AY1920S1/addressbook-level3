package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.BUDGET_ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedBudgetTest {
    private static final String INVALID_CATEGORY = "#food";

    private static final String VALID_AMOUNT = BUDGET_ONE.getBudget().toString();
    private static final String VALID_DATE = BUDGET_ONE.getDeadline().toString();
    private static final List<JsonAdaptedCategory> VALID_CATEGORIES = BUDGET_ONE.getCategories().stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBudgetDetails_returnsBudget() throws Exception {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(BUDGET_ONE);
        assertEquals(BUDGET_ONE, budget.toModelType());
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budgetOne = new JsonAdaptedBudget(VALID_AMOUNT, null, VALID_DATE, VALID_CATEGORIES);
        assertThrows(IllegalValueException.class, budgetOne::toModelType);
    }

    @Test
    public void toModelType_nullInitialAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budgetOne = new JsonAdaptedBudget(null, VALID_AMOUNT, VALID_DATE, VALID_CATEGORIES);
        assertThrows(IllegalValueException.class, budgetOne::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, VALID_AMOUNT, null, VALID_CATEGORIES);
        assertThrows(IllegalValueException.class, budget::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidTags = new ArrayList<>(VALID_CATEGORIES);
        invalidTags.add(new JsonAdaptedCategory(INVALID_CATEGORY));
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_AMOUNT, VALID_AMOUNT, VALID_DATE, invalidTags);
        assertThrows(IllegalValueException.class, budget::toModelType);
    }
}
