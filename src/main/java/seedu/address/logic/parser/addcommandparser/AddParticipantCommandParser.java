package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;
import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entitylist.ParticipantList;

/**
 * Parses input arguments and creates a new {@link AddParticipantCommand} object.
 */
public class AddParticipantCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddParticipantCommand}
     * and returns an {@code AddParticipantCommand} object for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public AddParticipantCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        if (!AlfredParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    seedu.address.logic.commands.AddCommand.MESSAGE_USAGE));
        }

        Name name = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = AlfredParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = AlfredParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Id id = new ParticipantList().generateId();
        Participant participant = new Participant(name, id, email, phone);

        return new AddParticipantCommand(participant);
    }
}
