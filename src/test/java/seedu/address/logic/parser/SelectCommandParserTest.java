package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;

class SelectCommandParserTest {

	private SelectCommandParser parser = new SelectCommandParser();

	@Test
	public void parse_emptyArg_throwsParseException() {
		assertParseFailure(parser, "b", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
	}

	@Test
	public void parse_validArgs_returnsSelectCommand() {
		assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_PERSON));
	}
}