package dukecooks.logic.parser.recipe;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CALORIES;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CARBS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_FATS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEINGREDIENT;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.recipe.components.Ingredient;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INGREDIENT, PREFIX_REMOVEINGREDIENT,
                        PREFIX_CALORIES, PREFIX_CARBS, PREFIX_FATS, PREFIX_PROTEIN);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecipeCommand.MESSAGE_USAGE), pe);
        }

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecipeDescriptor.setName(ParserUtil.parseRecipeName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
            editRecipeDescriptor.setCalories(ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get()));
        }
        if (argMultimap.getValue(PREFIX_CARBS).isPresent()) {
            editRecipeDescriptor.setCarbs(ParserUtil.parseCarbs(argMultimap.getValue(PREFIX_CARBS).get()));
        }
        if (argMultimap.getValue(PREFIX_FATS).isPresent()) {
            editRecipeDescriptor.setFats(ParserUtil.parseFats(argMultimap.getValue(PREFIX_FATS).get()));
        }
        if (argMultimap.getValue(PREFIX_PROTEIN).isPresent()) {
            editRecipeDescriptor.setProtein(ParserUtil.parseProtein(argMultimap.getValue(PREFIX_PROTEIN).get()));
        }

        parseIngredientsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT))
                .ifPresent(editRecipeDescriptor::addIngredients);

        parseIngredientsForEdit(argMultimap.getAllValues(PREFIX_REMOVEINGREDIENT))
                .ifPresent(editRecipeDescriptor::removeIngredients);

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
