package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRecipeCommand;
import seedu.address.logic.commands.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Ingredient;

/**
 * Parses input arguments and creates a new EditRecipeCommand object
 */
public class EditRecipeCommandParser implements Parser<EditRecipeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRecipeCommand
     * and returns an EditRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRecipeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INGREDIENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecipeCommand.MESSAGE_USAGE), pe);
        }

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecipeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        parseIngredientsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT))
                .ifPresent(editRecipeDescriptor::setIngredients);

        if (!editRecipeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecipeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecipeCommand(index, editRecipeDescriptor);
    }

    /**
     * Parses {@code Collection<String> ingredients} into a {@code Set<Ingredient>} if {@code ingredients}
     * is non-empty.
     * If {@code ingredients} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Ingredient>} containing zero ingredients.
     */
    private Optional<Set<Ingredient>> parseIngredientsForEdit(Collection<String> ingredients) throws ParseException {
        assert ingredients != null;

        if (ingredients.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> ingredientSet = ingredients.size() == 1 && ingredients.contains("")
                ? Collections.emptySet() : ingredients;
        return Optional.of(ParserUtil.parseIngredients(ingredientSet));
    }

}
