package seedu.address.logic.parser;

/*import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;*/

import java.time.LocalDateTime;

/*import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;*/

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SetDeadlineCommandParserStub implements Parser<SetDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetDeadlineCommand parse(String args) throws ParseException {
        Index stubIndex1 = Index.fromOneBased(2);
        LocalDateTime stubDateTime1 = LocalDateTime.parse("2015-02-20T06:30:00");
        return new SetDeadlineCommand(stubIndex1, stubDateTime1);
    }
}
