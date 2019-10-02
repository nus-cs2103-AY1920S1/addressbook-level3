package seedu.address.logic.parser.AddCommandParsers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entitylist.ParticipantList;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddParticipantCommandParser implements Parser<AddParticipantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddParticipantCommand parse(String args) throws ParseException {

        /**
         * Added the below code as a placeholder. We will replace it with proper code
         * once the Participant class is finalised.
         */

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name name = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = AlfredParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = AlfredParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Id id = new ParticipantList().generateID();

        Participant participant = new Participant(name, id, email, phone);

        return new AddParticipantCommand(participant);
    }
}
