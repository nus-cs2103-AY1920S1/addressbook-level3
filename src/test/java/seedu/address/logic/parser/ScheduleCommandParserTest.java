package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ScheduleCommandParserTest {

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    void parse_success() {
        CommandParserTestUtil.assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString(),
                new ScheduleCommand(new ArrayList<>(List.of(ALICE.getName())))
        );

        CommandParserTestUtil.assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_NAME + ZACK.getName().toString(),
                new ScheduleCommand(new ArrayList<>(List.of(ALICE.getName(), ZACK.getName())))
        );
    }

    @Test
    void parse_failure() {
        assertThrows(ParseException.class, () ->
                parser.parse(WHITESPACE));
    }
}
