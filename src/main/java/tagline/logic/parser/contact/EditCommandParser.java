package tagline.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_ADDRESS;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_DESCRIPTION;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_EMAIL;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_NAME;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import tagline.commons.core.index.Index;
import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditContactCommand object
 */
public class EditCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditContactCommand
     * and returns an EditContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ContactParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE), pe);
        }

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ContactParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ContactParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editContactDescriptor
                    .setAddress(ContactParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editContactDescriptor.setDescription(ContactParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ContactParserUtil.parseTags(tagSet));
    }

}
