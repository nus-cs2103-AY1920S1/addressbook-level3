package seedu.billboard.model.statistics.generators;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.testutil.TypicalExpenses;


class HeatMapGeneratorTest {
    private final HeatMapGenerator heatMapGenerator = new HeatMapGenerator();

    @Test
    void generate_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> heatMapGenerator.generate(null));
        assertThrows(NullPointerException.class, () -> heatMapGenerator.generate(new ArrayList<>(), null));
        assertThrows(NullPointerException.class, () ->
                heatMapGenerator.generate(null, DateRange.from(LocalDate.MIN, LocalDate.MIN.plusYears(1))));
    }

    @Test
    void generate_emptyListInput_returnsEmptyOutput() {
        assertThat(heatMapGenerator.generate(new ArrayList<>()).getHeatMapValues(), is(empty()));
    }

    // Based of expenses list from {@code TypicalExpenses#getTypicalExpenses}.
    @Test
    void generate_nonEmptyList_returnsCorrectExpenseHeatMap() {
        List<Expense> expenses = TypicalExpenses.getTypicalExpenses();
        List<EnumMap<DayOfWeek, Amount>> heatmapValues = heatMapGenerator.generate(expenses).getHeatMapValues();

        // either size 54 or 55 depending on whether additional week was added when
        assertThat(heatmapValues, hasSize(54));
        assertThat(heatmapValues.get(0), is(anEmptyMap()));

        assertThat(heatmapValues.get(53), is(aMapWithSize(1)));
        assertThat(heatmapValues.get(53), hasEntry(DayOfWeek.TUESDAY, new Amount("23.50")));

        assertThat(heatmapValues.get(48), is(aMapWithSize(1)));
        assertThat(heatmapValues.get(48), hasEntry(DayOfWeek.SATURDAY, new Amount("4.20")));

        assertThat(heatmapValues.get(47), is(aMapWithSize(1)));
        assertThat(heatmapValues.get(47), hasEntry(DayOfWeek.TUESDAY, new Amount("10.00")));

        assertThat(heatmapValues.get(30), is(aMapWithSize(1)));
    }
}
