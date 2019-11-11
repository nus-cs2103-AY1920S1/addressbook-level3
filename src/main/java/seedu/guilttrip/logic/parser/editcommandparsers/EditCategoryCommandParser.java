package seedu.guilttrip.logic.parser.editcommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_OLD_NAME;

import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand.EditCategoryDescriptor;
import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Category;

/**
 * Parses input arguments and creates a new EditCategoryCommand object
 */
public class EditCategoryCommandParser implements Parser<EditCategoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCategoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_OLD_NAME, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_OLD_NAME, PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCategoryCommand.MESSAGE_USAGE));
        }

        String typeOfCategory = argMultimap.getValue(PREFIX_CATEGORY).get().toLowerCase();
        String categoryName = argMultimap.getValue(PREFIX_OLD_NAME).get().toLowerCase();
        String newCategoryName = argMultimap.getValue(PREFIX_DESC).get().toLowerCase();

        Category categoryToEdit = ParserUtil.parseCategory(categoryName, typeOfCategory);

        EditCategoryDescriptor editCategoryDescriptor = new EditCategoryDescriptor();
        editCategoryDescriptor.setCategoryName(newCategoryName);
        editCategoryDescriptor.setCategoryType(ParserUtil.parseCategoryType(typeOfCategory));

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
