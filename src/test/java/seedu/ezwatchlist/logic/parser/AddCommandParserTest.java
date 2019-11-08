package seedu.ezwatchlist.logic.parser;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.AddCommand;

import seedu.ezwatchlist.logic.parser.exceptions.ParseException;



public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    void parse() throws ParseException {
        AddCommand addCommand = new AddCommand(1);

    }

}
