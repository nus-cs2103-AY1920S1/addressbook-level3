package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.DeleteAppointmentCommand;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parseTest() {

        assertParseSuccess(parser, "test d/7",
                new DeleteAppointmentCommand("test", 7));

        assertParseSuccess(parser, "test",
                new DeleteAppointmentCommand("test", -1));

    }
}
