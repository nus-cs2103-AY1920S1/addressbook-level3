package seedu.address.logic.parser.viewcommandparser;

import seedu.address.logic.commands.viewcommand.ViewMentorCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ViewMentorCommandParser implements Parser<ViewMentorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewMentorCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            throw new ParseException(String.format(ViewMentorCommand.MESSAGE_USAGE));
        }
        Id id;

        try {
            id = AlfredParserUtil.parseIndex(args, PrefixType.M);
        } catch (ParseException p) {
            throw new ParseException(String.format(ViewMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX), p);
        }
        return new ViewMentorCommand(id);
    }
}
