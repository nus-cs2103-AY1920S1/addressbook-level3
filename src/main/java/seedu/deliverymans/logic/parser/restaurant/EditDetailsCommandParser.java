package seedu.deliverymans.logic.parser.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.deliverymans.logic.commands.restaurant.EditDetailsCommand;
import seedu.deliverymans.logic.commands.restaurant.EditDetailsCommand.EditRestaurantDescriptor;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Tag;

/**
 * Parses input arguments and creates a new EditDetailsCommand object
 */
public class EditDetailsCommandParser implements Parser<EditDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDetailsCommand
     * and returns an EditDetailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDetailsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()
                || ParserUtil.hasRepeatedPrefix(args, PREFIX_NAME, PREFIX_LOCATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDetailsCommand.MESSAGE_USAGE));
        }

        EditRestaurantDescriptor editRestaurantDescriptor = new EditRestaurantDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRestaurantDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editRestaurantDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editRestaurantDescriptor::setTags);

        if (!editRestaurantDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDetailsCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDetailsCommand(editRestaurantDescriptor);
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
