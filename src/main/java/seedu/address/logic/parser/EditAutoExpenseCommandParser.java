package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
// import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAutoExpenseCommand;
import seedu.address.logic.commands.EditAutoExpenseCommand.EditAutoExpenseDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditAutoExpenseCommand object
 */
public class EditAutoExpenseCommandParser implements Parser<EditAutoExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAutoExpenseCommand
     * and returns an EditAutoExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAutoExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_DATE, PREFIX_AMOUNT,
                PREFIX_TAG, PREFIX_FREQ, PREFIX_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT, argMultimap.getPreamble()), pe);
        }

        EditAutoExpenseCommand.EditAutoExpenseDescriptor editAutoExpenseDescriptor = new EditAutoExpenseDescriptor();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editAutoExpenseDescriptor.setDesc(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editAutoExpenseDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editAutoExpenseDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editAutoExpenseDescriptor.setCategory(new Category(argMultimap.getValue(PREFIX_CATEGORY).get(), "Expense"));
        }

        if (argMultimap.getValue(PREFIX_FREQ).isPresent()) {
            editAutoExpenseDescriptor.setFrequency(ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQ).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editAutoExpenseDescriptor::setTags);

        if (!editAutoExpenseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAutoExpenseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAutoExpenseCommand(index, editAutoExpenseDescriptor);
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
