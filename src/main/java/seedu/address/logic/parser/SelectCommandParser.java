package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class SelectCommandParser implements Parser<SelectCommand> {

	public SelectCommand parse(String args) throws ParseException {
		String trimmedArgs = args.trim();
		if(trimmedArgs.isEmpty()) {
			throw new ParseException(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}

		String[] nameKeyWords = trimmedArgs.split("\\s+");
		String[] nameKey = new String[1];
		nameKey[0] = nameKeyWords[0];

		return new SelectCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKey)));
	}
}
