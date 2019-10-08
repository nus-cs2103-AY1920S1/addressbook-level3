package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditClaimCommand object
 */
public class EditIncomeCommandParser implements Parser<EditIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditClaimCommand
     * and returns an EditClaimCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIncomeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CASH, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                EditIncomeCommand.MESSAGE_USAGE), pe);
        }

        EditIncomeDescriptor editIncomeDescriptor = new EditIncomeDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editIncomeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_CASH).isPresent()) {
            editIncomeDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_CASH).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editIncomeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editIncomeDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editIncomeDescriptor::setTags);

        if (!editIncomeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditIncomeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditIncomeCommand(index, editIncomeDescriptor);
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
