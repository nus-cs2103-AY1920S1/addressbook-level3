package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;

class FullCommandParserTest {

    @Test
    void parse() throws ParseException {
        new FullCommandParser()
            .parse("add_event DESCRIPITON DATE_TIME --end DATE_TIME --remind DATE_TIME");
        new FullCommandParser()
            .parse("add_event \"Celebrate Rori’s Birthday\" \"18/08/2019 16:00\" --end \"18/08/2019 20:00\"");
    }
}