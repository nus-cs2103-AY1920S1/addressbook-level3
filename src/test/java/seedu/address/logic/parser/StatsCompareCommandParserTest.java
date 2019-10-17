package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class StatsCompareCommandParserTest {
    private StatsCompareCommandParser parser = new StatsCompareCommandParser();

    @Test
    void parse_optionalFields_success() throws ParseException {
        assertNotNull(parser.parse(String.format(" %s01-10-2019 %s31-10-2019 %smonth",
                PREFIX_FIRST_START_DATE,
                PREFIX_SECOND_START_DATE,
                PREFIX_PERIOD)));
    }
}


