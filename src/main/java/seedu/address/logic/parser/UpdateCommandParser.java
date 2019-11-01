package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_CATEGORY);

        String type;
        Index index;

        try {
            type = ParserUtil.parseType(argMultimap.getPreamble());
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }
        UpdateTransactionDescriptor updateTransactionDescriptor = new UpdateTransactionDescriptor();
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            updateTransactionDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            updateTransactionDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY))
            .ifPresent(updateTransactionDescriptor::setCategories);

        if (!updateTransactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }

        /* handles negative amount */
        if (argMultimap.getValue(PREFIX_AMOUNT).get().toCharArray()[0] == (NEGATIVE_AMOUNT_SIGN)) {
            throw new ParseException(String.format(UpdateCommand.MESSAGE_AMOUNT_NEGATIVE));
        }

        /* handles 0 value */
        if (argMultimap.getValue(PREFIX_AMOUNT).get().toCharArray()[0] == (ZERO_AMOUNT)
                && argMultimap.getValue(PREFIX_AMOUNT).get().toCharArray().length == 1) {
            throw new ParseException(String.format(UpdateCommand.MESSAGE_AMOUNT_ZERO));

        }
        /* handles amount above 1billion */
        if (argMultimap.getValue(PREFIX_AMOUNT).get().length() > MAX_AMOUNT_LENGTH) {
            throw new ParseException(String.format(UpdateCommand.MESSAGE_AMOUNT_OVERFLOW));
        }


        return new UpdateCommand(type, index, updateTransactionDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = categories.size() == 1 && categories.contains("")
            ? Collections.emptySet() : categories;
        return Optional.of(ParserUtil.parseCategories(tagSet));
    }
}
