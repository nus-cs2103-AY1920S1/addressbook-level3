// @@author chrischenhui
package seedu.address.logic.parser.home;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.wordbankcommands.CreateCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Word;

/**
 * Parses input arguments and creates a new CreateCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String name) throws ParseException {
        String bankName = name.trim();
        if (bankName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        } else if (!Word.isValidWord(bankName)) {
            throw new ParseException(String.format(Word.MESSAGE_CONSTRAINTS, CreateCommand.MESSAGE_USAGE));
        }
        return new CreateCommand(bankName);
    }

}
