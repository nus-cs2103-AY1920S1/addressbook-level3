package seedu.address.logic.parser.viewcommandparser;

import seedu.address.logic.commands.viewcommand.ViewParticipantCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ViewParticipantCommandParser implements Parser<ViewParticipantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewParticipantCommand parse(String args) throws ParseException {
        Id id;

        try {
            id = AlfredParserUtil.parseIndex(args, PrefixType.P);
        } catch (ParseException p) {
            throw new ParseException(String.format(ViewParticipantCommand
                    .MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX), p);
        }
        return new ViewParticipantCommand(id);
    }
}
