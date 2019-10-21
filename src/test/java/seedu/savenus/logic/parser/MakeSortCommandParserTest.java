package seedu.savenus.logic.parser;

import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.MakeSortCommand;

public class MakeSortCommandParserTest {
    private MakeSortCommandParser parser = new MakeSortCommandParser();

    @Test
    public void parse_emptyFields_success() {
        MakeSortCommand command = new MakeSortCommand(new ArrayList<String>());
        assertParseSuccess(parser, " ", command);
    }
}
