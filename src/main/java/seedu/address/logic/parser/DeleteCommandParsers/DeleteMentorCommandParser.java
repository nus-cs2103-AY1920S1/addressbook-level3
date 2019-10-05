package seedu.address.logic.parser.DeleteCommandParsers;

import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.commands.deletecommand.DeleteMentorCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteMentorCommandParser implements Parser<DeleteCommand> {

    private static final String MESSAGE_USAGE = "Wrong usage.";

    @Override
    public DeleteMentorCommand parse(String args) throws ParseException {
        try {
            Id id = AlfredParserUtil.parseIndex(args, PrefixType.M);
            return new DeleteMentorCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }
    }

}
