package unrealunity.visit.logic.parser;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.DeleteAppointmentCommand;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parseTest() {

        CommandParserTestUtil.assertParseSuccess(parser, "test d/7",
                new DeleteAppointmentCommand("test", 7));

        CommandParserTestUtil.assertParseSuccess(parser, "test",
                new DeleteAppointmentCommand("test", -1));

    }
}
