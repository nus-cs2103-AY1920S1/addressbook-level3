package seedu.address.logic.parser.editcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.editcommand.EditParticipantCommand;
import seedu.address.logic.commands.editcommand.EditParticipantCommand.EditParticipantDescriptor;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new {@link EditParticipantCommand} object.
 */
public class EditParticipantCommandParser implements Parser<EditParticipantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditParticipantCommand}
     * and returns an {@code EditParticipantCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditParticipantCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        Id id;

        try {
            id = AlfredParserUtil.parseIndex(argMultimap.getPreamble(), PrefixType.P);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    seedu.address.logic.commands.EditCommand.MESSAGE_USAGE), pe);
        }

        EditParticipantDescriptor editParticipantDescriptor = new EditParticipantDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editParticipantDescriptor.setName(AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editParticipantDescriptor.setEmail(AlfredParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editParticipantDescriptor.setPhone(AlfredParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (!editParticipantDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditParticipantCommand.MESSAGE_NOT_EDITED);
        }

        return new EditParticipantCommand(id, editParticipantDescriptor);
    }
}
