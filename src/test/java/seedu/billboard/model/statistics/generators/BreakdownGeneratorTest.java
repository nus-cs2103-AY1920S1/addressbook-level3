package seedu.billboard.model.statistics.generators;

import static seedu.billboard.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.FOOD;
import static seedu.billboard.testutil.TypicalExpenses.GROCERIES;
import static seedu.billboard.testutil.TypicalExpenses.MOVIE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.hamcrest.collection.IsMapContaining;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseBreakdown;
import seedu.billboard.testutil.ExpenseBuilder;
import seedu.billboard.testutil.TypicalExpenses;


public class BreakdownGeneratorTest {
    private final BreakdownGenerator breakdownGenerator = new BreakdownGenerator();

    @Test
    public void generate_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> breakdownGenerator.generate(null));
    }

    @Test
    public void generate_emptyList_returnsExpenseBreakdownWithEmptyMap() {
        ExpenseBreakdown actualBreakdown = breakdownGenerator.generate(new ArrayList<>());
        assertTrue(actualBreakdown.getBreakdownValues().isEmpty());
    }

    @Test
    public void generate_listOfExpensesWithNoTags_returnsExpenseBreakdownWithEmptyMap() {
        // GROCERIES in TypicalExpenses has no tags
        ExpenseBreakdown actualBreakdown = breakdownGenerator.generate(Collections.singletonList(GROCERIES));
        assertTrue(actualBreakdown.getBreakdownValues().isEmpty());
    }

    @Test
    public void generate_listOfOneExpenseWithSomeTags_returnsCorrectExpenseBreakdown() {
        // FOOD in TypicalExpenses has 2 tags, "monday" and "friends"
        ExpenseBreakdown actualBreakdown = breakdownGenerator.generate(Collections.singletonList(FOOD));
        assertThat(actualBreakdown.getBreakdownValues(),
                IsMapContaining.hasEntry("monday", Collections.singletonList(FOOD)));
        assertThat(actualBreakdown.getBreakdownValues(),
                IsMapContaining.hasEntry("friends", Collections.singletonList(FOOD)));
    }

    @Test
    public void generate_listOfExpensesWithSharedTags_returnsExpenseBreakdownWithExpensesUnderSameTag() {
        // FOOD in TypicalExpenses has 2 tags, "monday" and "friends"
        Expense testExpense = new ExpenseBuilder().withTags("monday", "friends", "test").build();
        List<Expense> expenses = List.of(FOOD, testExpense);

        ExpenseBreakdown actualBreakdown = breakdownGenerator.generate(expenses);
        Map<String, ? extends List<? extends Expense>> actualMap = actualBreakdown.getBreakdownValues();

        assertThat(actualMap, IsMapContaining.hasEntry("monday", List.of(FOOD, testExpense)));
        assertThat(actualMap, IsMapContaining.hasEntry("friends", List.of(FOOD, testExpense)));
        assertThat(actualMap,
                IsMapContaining.hasEntry("test", Collections.singletonList(testExpense)));
    }

    // Based of the expenses in TypicalExpenses#getTypicalExpenses
    @Test
    public void generate_generalCase_returnsCorrectExpenseBreakdown() {
        ExpenseBreakdown actualBreakdown = breakdownGenerator.generate(TypicalExpenses.getTypicalExpenses());
        Map<String, ? extends List<? extends Expense>> actualMap = actualBreakdown.getBreakdownValues();

        assertThat(actualMap, IsMapContaining.hasEntry("bills", Collections.singletonList(BILLS)));
        assertThat(actualMap, IsMapContaining.hasEntry("monday", Collections.singletonList(FOOD)));
        assertThat(actualMap, IsMapContaining.hasEntry("friends", Collections.singletonList(FOOD)));
        assertThat(actualMap, not(IsMapContaining.hasValue(GROCERIES)));
        assertThat(actualMap, IsMapContaining.hasEntry("leisure", Collections.singletonList(MOVIE)));
    }

}
