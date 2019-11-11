package seedu.address.logic.finance.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_FROM;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TO;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.finance.commands.EditCommand.EditLogEntryDescriptor;
import seedu.address.logic.finance.commands.EditCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.attributes.Category;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DAY,
                        PREFIX_DESCRIPTION, PREFIX_CATEGORY,
                        PREFIX_TRANSACTION_METHOD,
                        PREFIX_PLACE, PREFIX_FROM, PREFIX_TO);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditLogEntryDescriptor editLogEntryDescriptor = new EditCommand.EditLogEntryDescriptor();

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editLogEntryDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            editLogEntryDescriptor.setTransactionDate(ParserUtil
                    .parseTransactionDate(argMultimap.getValue(PREFIX_DAY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editLogEntryDescriptor.setDesc(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_TRANSACTION_METHOD).isPresent()) {
            editLogEntryDescriptor.setTMethod(ParserUtil.parseTransactionMethod(
                    argMultimap.getValue(PREFIX_TRANSACTION_METHOD).get()));
        }
        if (argMultimap.getValue(PREFIX_PLACE).isPresent()) {
            editLogEntryDescriptor.setPlace(ParserUtil.parsePlace(
                    argMultimap.getValue(PREFIX_PLACE).get()));
        }
        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            editLogEntryDescriptor.setFrom(ParserUtil.parsePerson(
                    argMultimap.getValue(PREFIX_FROM).get()));
        }
        if (argMultimap.getValue(PREFIX_TO).isPresent()) {
            editLogEntryDescriptor.setTo(ParserUtil.parsePerson(
                    argMultimap.getValue(PREFIX_TO).get()));
        }
        parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY))
                .ifPresent(editLogEntryDescriptor::setCategories);

        if (!editLogEntryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editLogEntryDescriptor);
    }

    /**
     * Parses {@code Collection<String> cats} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code category} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> cats) throws ParseException {
        assert cats != null;

        if (cats.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> catSet = cats.size() == 1 && cats.contains("") ? Collections.emptySet() : cats;
        return Optional.of(ParserUtil.parseCategories(catSet));
    }

}
