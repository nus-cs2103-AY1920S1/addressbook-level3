package seedu.address.logic.parser.ListCommandParsers;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ListParticipantCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {

        // The return type of this command will also have to be corrected
        // to return a ListParticipantCommand object as designed by John instead of an
        // EditCommand Object.

        /**
         * This is just placeholder code. We will implement proper code
         * when the Participant class has been finalised.
         */

        return null;
    }
}
