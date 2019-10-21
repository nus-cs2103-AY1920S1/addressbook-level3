package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.awt.*;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.aesthetics.FontColourCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class FontColourParser implements Parser<FontColourCommand> {



    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an EditCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FontColourCommand parse(String args) throws ParseException {
        requireNonNull(args);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        //        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
        //            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        //        }
        //        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
        //            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        //        }
        //        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
        //            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        //        }
        //        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
        //            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)
        //            .get()));
        //        }
        //        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty. If {@code tags}
     * contain only one element which is an empty string, it will be parsed into a {@code Set<Tag>} containing zero
     * tags.
     */
    //    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
    //        assert tags != null;
    //
    //        if (tags.isEmpty()) {
    //            return Optional.empty();
    //        }
    //        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
    //        return Optional.of(ParserUtil.parseTags(tagSet));
    //    }

}
