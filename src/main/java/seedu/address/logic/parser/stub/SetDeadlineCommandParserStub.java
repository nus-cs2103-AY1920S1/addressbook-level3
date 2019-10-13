package seedu.address.logic.parser.stub;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.tag.Tag;

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
        }
        count ++;
        return new SetDeadlineCommand(stubIndex1, stubDateTime);
    }
}
