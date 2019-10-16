package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditMemberCommand object
 */
public class EditMemberCommandParser implements Parser<EditMemberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMemberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_NAME, PREFIX_MEMBER_TAG);

        MemberId id;

        try {
            id = ParserUtil.parseMemberId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMemberCommand.MESSAGE_USAGE), pe);
        }

        EditMemberCommand.EditMemberDescriptor editMemberDescriptor = new EditMemberCommand.EditMemberDescriptor();
        if (argMultimap.getValue(PREFIX_MEMBER_NAME).isPresent()) {
            editMemberDescriptor.setName(ParserUtil.parseMemberName(argMultimap.getValue(PREFIX_MEMBER_NAME).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_MEMBER_TAG)).ifPresent(editMemberDescriptor::setTags);

        if (!editMemberDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMemberCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMemberCommand(id, editMemberDescriptor);
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
