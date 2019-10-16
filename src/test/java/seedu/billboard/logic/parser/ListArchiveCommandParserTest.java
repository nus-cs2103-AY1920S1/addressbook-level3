package seedu.billboard.logic.parser;

import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.ListArchiveCommand;

public class ListArchiveCommandParserTest {

    private ListArchiveCommandParser parser = new ListArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsListArchiveCommand() {
        assertParseSuccess(parser, "hobbies", new ListArchiveCommand("hobbies"));
    }

}
