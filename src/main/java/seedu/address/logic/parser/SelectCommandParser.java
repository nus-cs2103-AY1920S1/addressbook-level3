package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class SelectCommandParser implements Parser<SelectCommand> {

	public SelectCommand parse(String args) throws ParseException {
		try {
			Index index = ParserUtil.parseIndex(args);
			return new SelectCommand(index);
		} catch (ParseException pe) {
			throw new ParseException(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
		}
	}
}
