package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LookAtGroupMemberCommand;
import seedu.address.model.person.Name;

public class LookAtGroupMemberCommandParserTest {
    private static final List<Name> namesParsed = List.of(new Name("Alex"), new Name("Tim"), new Name("James"));
    private Parser<LookAtGroupMemberCommand> parser = new LookAtGroupMemberCommandParser();
    @Test
    public void wrongFlagsGivenToParser() {
        assertParseFailure(parser, "n/Alex g/Benjamin", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LookAtGroupMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseSuccess() {
        assertParseSuccess(parser, " n/Alex n/Tim n/James", new LookAtGroupMemberCommand(namesParsed));
        //With many white spaces.
        assertParseSuccess(parser, " n/Alex     n/Tim   n/James", new LookAtGroupMemberCommand(namesParsed));
        //Different ordering.
        assertParseSuccess(parser, " n/Tim n/Alex n/James", new LookAtGroupMemberCommand(namesParsed));
    }
}
