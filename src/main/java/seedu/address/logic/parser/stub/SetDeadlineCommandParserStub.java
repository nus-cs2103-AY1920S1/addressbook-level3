package seedu.address.logic.parser.stub;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;


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
        LocalDateTime stubDateTime = null;
        LocalDateTime stubDateTime0 = LocalDateTime.parse("2015-02-20T06:30:00");
        LocalDateTime stubDateTime1 = LocalDateTime.parse("1997-10-24T09:55:55");
        LocalDateTime stubDateTime2 = LocalDateTime.now();
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
