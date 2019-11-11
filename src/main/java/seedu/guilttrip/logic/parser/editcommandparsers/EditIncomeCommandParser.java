package seedu.guilttrip.logic.parser.editcommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand;
import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditIncomeCommand object
 */
public class EditIncomeCommandParser implements Parser<EditIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIncomeCommand
     * and returns an EditIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIncomeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DESC, PREFIX_DATE, PREFIX_AMOUNT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format
                    (MESSAGE_INVALID_COMMAND_FORMAT, EditIncomeCommand.MESSAGE_USAGE), pe);
        }

        EditIncomeCommand.EditIncomeDescriptor editIncomeDescriptor = new EditIncomeDescriptor();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editIncomeDescriptor.setDesc(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editIncomeDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editIncomeDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            Category editedCategory = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get(),
                    "INCOME");
            editIncomeDescriptor.setCategory(editedCategory);
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
