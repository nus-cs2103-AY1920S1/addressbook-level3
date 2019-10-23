package seedu.address.logic.parser.stub;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SetDeadlineCommandParserStub implements Parser<SetDeadlineCommand> {

    private static int count = 1;
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetDeadlineCommand parse(String args) throws ParseException {
        Index stubIndex1 = Index.fromOneBased(2);
        LocalDateTime stubDateTime;
        LocalDateTime stubDateTime0;
        LocalDateTime stubDateTime1;
        LocalDateTime stubDateTime2;
        try {
            stubDateTime0 = DateTimeUtil.parseDateTime("15/12/201  13:00");
            stubDateTime1 = DateTimeUtil.parseDateTime("10/10/201  18:00");
            stubDateTime2 = DateTimeUtil.parseDateTime("20/10/201  18:00");
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDeadlineCommand.MESSAGE_USAGE));
        }
        switch (count % 3) {
        case 0:
            stubDateTime = stubDateTime0;
            break;
        case 1:
            stubDateTime = stubDateTime1;
            break;
        case 2:
            stubDateTime = stubDateTime2;
            break;
        default:
            stubDateTime = null;
        }
        count++;
        return new SetDeadlineCommand(stubIndex1, stubDateTime);
    }
}
