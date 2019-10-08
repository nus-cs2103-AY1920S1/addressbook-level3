package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;

import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.commands.addcommand.AddMentorCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entitylist.MentorList;

/**
 * Parses input arguments and creates a new {@link AddMentorCommand} object.
 */
public class AddMentorCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddMentorCommand}
     * and returns an {@code AddMentorCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddMentorCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ORGANISATION,
                        PREFIX_SUBJECT_NAME);

        if (!AlfredParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ORGANISATION, PREFIX_SUBJECT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    seedu.address.logic.commands.AddCommand.MESSAGE_USAGE));
        }

        Name name = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = AlfredParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = AlfredParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Name organisation = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_ORGANISATION).get());
        SubjectName subject = AlfredParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT_NAME).get());
        Id id = new MentorList().generateId();

        Mentor mentor = new Mentor(name, id, phone, email, organisation, subject);

        return new AddMentorCommand(mentor);
    }
}
