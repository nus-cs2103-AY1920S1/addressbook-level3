package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPasswordCommand;
import seedu.address.logic.commands.EditPasswordCommand.EditPasswordDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;





/**
 * Parses input arguments and creates a new EditPasswordCommand object
 */
public class EditPasswordCommandParser implements Parser<EditPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPasswordCommand
     * and returns an EditPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPasswordCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DESCRIPTION, PREFIX_USERNAME,
                        PREFIX_PASSWORDVALUE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPasswordCommand.MESSAGE_USAGE), pe);
        }

        EditPasswordDescriptor editPasswordDescriptor = new EditPasswordDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPasswordDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_USERNAME).isPresent()) {
            editPasswordDescriptor.setUsername(
                    ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PASSWORDVALUE).isPresent()) {
            editPasswordDescriptor.setPasswordValue(
                    ParserUtil.parsePasswordValue(argMultimap.getValue(PREFIX_PASSWORDVALUE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPasswordDescriptor::setTags);

        if (!editPasswordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPasswordCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPasswordCommand(index, editPasswordDescriptor);
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
