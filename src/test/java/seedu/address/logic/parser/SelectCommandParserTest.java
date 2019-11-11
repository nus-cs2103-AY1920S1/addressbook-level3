package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    void parseSuccess_allFieldsPresent() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_WEEK + "1"
                        + WHITESPACE + PREFIX_ID + "1",
                new SelectCommand(0, ALICE.getName(), 1)
        );
    }

    @Test
    void parseSuccess_emptyName() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_WEEK + "1"
                        + WHITESPACE + PREFIX_ID + "1",
                new SelectCommand(0, Name.emptyName(), 1)
        );
    }

    @Test
    void parseSuccess_emptyWeek() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_ID + "1",
                new SelectCommand(0, ALICE.getName(), 1)
        );
    }

    @Test
    void parseFailure_emptyId() {
        assertThrows(ParseException.class, () ->
                parser.parse(WHITESPACE + PREFIX_NAME + ALICE.getName().toString()));
    }

}
