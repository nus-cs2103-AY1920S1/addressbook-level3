package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditBudgetCommand.EditBudgetDescriptor;
import seedu.address.logic.commands.EditBudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditBudgetCommand object
 */
public class EditBudgetCommandParser implements Parser<EditBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBudgetCommand
     * and returns an EditBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_TIME, PREFIX_AMOUNT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format
                    (MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE), pe);
        }

        EditBudgetCommand.EditBudgetDescriptor editBudgetDescriptor = new EditBudgetDescriptor();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editBudgetDescriptor.setDesc(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
        }

        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editBudgetDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editBudgetDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editBudgetDescriptor::setTags);

        if (!editBudgetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBudgetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBudgetCommand(index, editBudgetDescriptor);
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
