package seedu.address.logic.parser.editcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommand.EditPhoneCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditPhoneCommand object
 */
public class EditPhoneCommandParser implements Parser<EditPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPhoneCommand
     * and returns an EditPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPhoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUM, PREFIX_SERIAL_NUM, PREFIX_PHONE_NAME,
                        PREFIX_BRAND, PREFIX_CAPACITY, PREFIX_COLOUR, PREFIX_COST, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPhoneCommand.MESSAGE_USAGE), pe);
        }

        EditPhoneDescriptor editPhoneDescriptor = new EditPhoneDescriptor();
        if (argMultimap.getValue(PREFIX_IDENTITY_NUM).isPresent()) {
            editPhoneDescriptor.setIdentityNumber(
                    ParserUtil.parseIdentityNumber(argMultimap.getValue(PREFIX_IDENTITY_NUM).get()));
        }

        if (argMultimap.getValue(PREFIX_SERIAL_NUM).isPresent()) {
            editPhoneDescriptor.setSerialNumber(
                    ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUM).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE_NAME).isPresent()) {
            editPhoneDescriptor.setPhoneName(
                    ParserUtil.parsePhoneName(argMultimap.getValue(PREFIX_PHONE_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_BRAND).isPresent()) {
            editPhoneDescriptor.setBrand(
                    ParserUtil.parseBrand(argMultimap.getValue(PREFIX_BRAND).get()));
        }
        if (argMultimap.getValue(PREFIX_CAPACITY).isPresent()) {
            editPhoneDescriptor.setCapacity(ParserUtil.parseCapacity(argMultimap.getValue(PREFIX_CAPACITY).get()));
        }

        if (argMultimap.getValue(PREFIX_COLOUR).isPresent()) {
            editPhoneDescriptor.setColour(ParserUtil.parseColour(argMultimap.getValue(PREFIX_COLOUR).get()));
        }

        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            editPhoneDescriptor.setCost(ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPhoneDescriptor::setTags);

        if (!editPhoneDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPhoneCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPhoneCommand(index, editPhoneDescriptor);
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
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
