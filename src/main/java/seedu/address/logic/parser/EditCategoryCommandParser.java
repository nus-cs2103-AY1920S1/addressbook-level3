package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.EditCategoryCommand.EditCategoryDescriptor;
import seedu.address.logic.commands.EditCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;

/**
 * Parses input arguments and creates a new EditCategoryCommand object
 */
public class EditCategoryCommandParser implements Parser<EditCategoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCategoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_CATEGORY, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_CATEGORY, PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCategoryCommand.MESSAGE_USAGE));
        }

        String typeOfCategory = argMultimap.getValue(PREFIX_TYPE).get().toLowerCase();
        String categoryName = argMultimap.getValue(PREFIX_CATEGORY).get().toLowerCase();
        String newCategoryName = argMultimap.getValue(PREFIX_DESC).get().toLowerCase();

        Category categoryToEdit = new Category(categoryName, typeOfCategory);

        EditCategoryDescriptor editCategoryDescriptor = new EditCategoryDescriptor();
        editCategoryDescriptor.setCategoryName(newCategoryName);
        editCategoryDescriptor.setCategoryType(typeOfCategory);

        return new EditCategoryCommand(categoryToEdit, editCategoryDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
