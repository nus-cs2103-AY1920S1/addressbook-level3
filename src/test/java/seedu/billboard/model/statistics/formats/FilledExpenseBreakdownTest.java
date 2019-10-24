package seedu.billboard.model.statistics.formats;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.testutil.TypicalExpenses;


public class FilledExpenseBreakdownTest {

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilledExpenseBreakdown(null));
    }

    // Based off the expenses found in TypicalExpenses
    @Test
    public void getTagBreakdownValues_normalInput_returnsEquivalentMap() {
        List<Expense> typicalExpenses = TypicalExpenses.getTypicalExpenses();
        Map<Tag, List<Expense>> testMap = new HashMap<>();

        typicalExpenses.forEach(expense ->
                expense.getTags().forEach(tag -> testMap.merge(tag, List.of(expense), (list1, list2) -> {
                        List<Expense> newList = new ArrayList<>(list1);
                        newList.addAll(list2);
                        return newList;
                    }
                )));

        FilledExpenseBreakdown breakdown = new FilledExpenseBreakdown(testMap);
        Map<Tag, List<Expense>> actualValues = breakdown.getTagBreakdownValues();

        assertThat(actualValues.entrySet(), equalTo(testMap.entrySet()));
    }
}
