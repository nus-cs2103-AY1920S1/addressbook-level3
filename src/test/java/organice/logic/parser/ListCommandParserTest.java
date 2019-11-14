package organice.logic.parser;

import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import organice.logic.commands.ListCommand;
import organice.model.person.Type;

public class ListCommandParserTest {

    private static final Type TYPE_PATIENT = new Type("patient");
    private static final Type TYPE_DONOR = new Type("donor");
    private static final Type TYPE_DOCTOR = new Type("doctor");

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {

        // no leading and trailing whitespaces -- no argument given
        ListCommand expectedListCommand = new ListCommand(null);
        assertParseSuccess(parser, "", expectedListCommand);

        // no leading and trailing whitespaces -- patient
        expectedListCommand = new ListCommand(TYPE_PATIENT);
        assertParseSuccess(parser, " t/patient", expectedListCommand);

        // no leading and trailing whitespaces -- donor
        expectedListCommand = new ListCommand(TYPE_DONOR);
        assertParseSuccess(parser, " t/donor", expectedListCommand);

        // no leading and trailing whitespaces -- doctor
        expectedListCommand = new ListCommand(TYPE_DOCTOR);
        assertParseSuccess(parser, " t/doctor", expectedListCommand);

        // leading and trailing whitespaces -- no argument given
        expectedListCommand = new ListCommand(null);
        assertParseSuccess(parser, "   ", expectedListCommand);

        // leading and trailing whitespaces -- patient
        expectedListCommand = new ListCommand(TYPE_PATIENT);
        assertParseSuccess(parser, "   t/patient", expectedListCommand);

        // leading and trailing whitespaces -- donor
        expectedListCommand = new ListCommand(TYPE_DONOR);
        assertParseSuccess(parser, "    t/donor", expectedListCommand);

        // leading and trailing whitespaces -- doctor
        expectedListCommand = new ListCommand(TYPE_DOCTOR);
        assertParseSuccess(parser, "      t/doctor", expectedListCommand);

        // wrong type specified
        assertParseFailure(parser, " t/student", Type.MESSAGE_CONSTRAINTS);
    }

}
