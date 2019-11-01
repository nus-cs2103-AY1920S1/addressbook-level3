package seedu.billboard.model.statistics.generators;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.statistics.formats.ExpenseHeatMap;
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
        ExpenseHeatMap heatMap = heatMapGenerator.generate(TypicalExpenses.getTypicalExpenses());
        heatMap.getHeatMapValues();
    }

    @Test
    void generateAsync() {
    }

}
