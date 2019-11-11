package seedu.pluswork.logic.parser.stub;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.task.SetDeadlineCommand;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class SetDeadlineCommandParserStub implements Parser<SetDeadlineCommand> {

    private static int count = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     *
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
