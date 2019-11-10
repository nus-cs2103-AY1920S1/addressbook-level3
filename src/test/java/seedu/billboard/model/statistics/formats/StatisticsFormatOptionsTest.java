package seedu.billboard.model.statistics.formats;

import org.junit.jupiter.api.Test;
import seedu.billboard.commons.core.date.DateInterval;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsFormatOptionsTest {

    @Test
    void emptyOptions() {
        StatisticsFormatOptions emptyOption = StatisticsFormatOptions.emptyOption();
        assertEquals(Optional.empty(), emptyOption.getNewGrouping());
        assertEquals(Optional.empty(), emptyOption.getNewDateInterval());
    }

    @Test
    void withOptions_allNullInputs_isEmptyOption() {
        StatisticsFormatOptions emptyOption = StatisticsFormatOptions.withOptions(null, null);
        assertEquals(StatisticsFormatOptions.emptyOption(), emptyOption);
        assertEquals(Optional.empty(), emptyOption.getNewGrouping());
        assertEquals(Optional.empty(), emptyOption.getNewDateInterval());
    }

    @Test
    void withOptions_combinationOfNullAndNonNullInputs_expectedResults() {
        StatisticsFormatOptions intervalOption = StatisticsFormatOptions.withOptions(DateInterval.WEEK, null);
        assertEquals(Optional.of(DateInterval.WEEK), intervalOption.getNewDateInterval());
        assertEquals(Optional.empty(), intervalOption.getNewGrouping());

        StatisticsFormatOptions groupingOption = StatisticsFormatOptions
                .withOptions(null, ExpenseGrouping.TAG);
        assertEquals(Optional.of(ExpenseGrouping.TAG), groupingOption.getNewGrouping());
        assertEquals(Optional.empty(), groupingOption.getNewDateInterval());
    }
}