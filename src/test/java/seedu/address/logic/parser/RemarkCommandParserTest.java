package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(parser, "1 " + PREFIX_REMARK + VALID_REMARK_BOB,
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB)));
    }

    @Test
    public void parse_allFieldsPresentIndexTwo_success() {
        assertParseSuccess(parser, "2 " + PREFIX_REMARK + VALID_REMARK_AMY,
                new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY)));
    }

    @Test
    public void parse_indexAbsent_fail() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREFIX_REMARK + VALID_REMARK_AMY, expectedMessage);
    }


}
