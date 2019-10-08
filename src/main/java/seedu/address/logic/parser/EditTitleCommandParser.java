package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTitleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.UserTag;

/**
 * Parses input arguments and creates a new EditTitleCommand object
 */
public class EditTitleCommandParser implements Parser<EditTitleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditTitleCommand and returns an EditTitleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTitleCommand parse(String args) throws ParseException {
        /*
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTitleCommand.MESSAGE_USAGE), pe);
        }

        EditStudyPlanDescriptor editStudyPlanDescriptor = new EditStudyPlanDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudyPlanDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editStudyPlanDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editStudyPlanDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editStudyPlanDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editStudyPlanDescriptor::setTags);

        if (!editStudyPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTitleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTitleCommand(index, editStudyPlanDescriptor);
        */
        return new EditTitleCommand();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if
     * {@code tags} is non-empty. If {@code tags} contain only one element which is
     * an empty string, it will be parsed into a {@code Set<Tag>} containing zero
     * tags.
     */
    /*
    private Optional<Set<UserTag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
    */
}
