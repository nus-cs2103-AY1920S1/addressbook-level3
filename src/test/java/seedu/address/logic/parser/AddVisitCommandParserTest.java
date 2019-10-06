package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddVisitCommand;
import seedu.address.model.person.VisitList;

public class AddVisitCommandParserTest {
    private AddVisitCommandParser parser = new AddVisitCommandParser();
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_VISIT + nonEmptyRemark;
        AddVisitCommand expectedCommand = new AddVisitCommand(INDEX_FIRST_PERSON, new VisitList(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_VISIT;
        expectedCommand = new AddVisitCommand(INDEX_FIRST_PERSON, new VisitList(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddVisitCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddVisitCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
