package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY1;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY2;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY3;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY4;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY5;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY6;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY7;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY1;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY2;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY3;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY4;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY5;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY6;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEDAY7;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand.EditMealPlanDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Parses input arguments and creates a new EditMealPlanCommand object
 */
public class EditMealPlanCommandParser implements Parser<EditMealPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditMealPlanCommand
     * and returns an EditMealPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMealPlanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DAY1, PREFIX_REMOVEDAY1,
                        PREFIX_DAY2, PREFIX_REMOVEDAY2, PREFIX_DAY3, PREFIX_REMOVEDAY3,
                        PREFIX_DAY4, PREFIX_REMOVEDAY4, PREFIX_DAY5, PREFIX_REMOVEDAY5,
                        PREFIX_DAY6, PREFIX_REMOVEDAY6, PREFIX_DAY7, PREFIX_REMOVEDAY7);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMealPlanCommand.MESSAGE_USAGE), pe);
        }

        EditMealPlanDescriptor editMealPlanDescriptor = new EditMealPlanDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editMealPlanDescriptor.setName(ParserUtil.parseMealPlanName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY1))
                .ifPresent(editMealPlanDescriptor::addDay1);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY1))
                .ifPresent(editMealPlanDescriptor::removeDay1);

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY2))
                .ifPresent(editMealPlanDescriptor::addDay2);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY2))
                .ifPresent(editMealPlanDescriptor::removeDay2);

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY3))
                .ifPresent(editMealPlanDescriptor::addDay3);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY3))
                .ifPresent(editMealPlanDescriptor::removeDay3);

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY4))
                .ifPresent(editMealPlanDescriptor::addDay4);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY4))
                .ifPresent(editMealPlanDescriptor::removeDay4);

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY5))
                .ifPresent(editMealPlanDescriptor::addDay5);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY5))
                .ifPresent(editMealPlanDescriptor::removeDay5);

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY6))
                .ifPresent(editMealPlanDescriptor::addDay6);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY6))
                .ifPresent(editMealPlanDescriptor::removeDay6);

        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_DAY7))
                .ifPresent(editMealPlanDescriptor::addDay7);
        parseRecipeNamesForEdit(argMultimap.getAllValues(PREFIX_REMOVEDAY7))
                .ifPresent(editMealPlanDescriptor::removeDay7);

        if (!editMealPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMealPlanCommand.MESSAGE_NOT_EDITED);
        }


        return new EditMealPlanCommand(index, editMealPlanDescriptor);
    }

    /**
     * Parses {@code Collection<String> recipeNames} into a {@code List<RecipeName>} if {@code recipeNames}
     * is non-empty.
     * If {@code recipeNames} contain only one element which is an empty string, it will be parsed into a
     * {@code List<RecipeName>} containing zero recipes.
     */
    private Optional<List<RecipeName>> parseRecipeNamesForEdit(Collection<String> recipeNames) throws ParseException {
        assert recipeNames != null;

        if (recipeNames.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> recipesNamesList = recipeNames.size() == 1 && recipeNames.contains("")
                                           ? Collections.emptyList() : recipeNames;
        return Optional.of(ParserUtil.parseRecipes(recipesNamesList));
    }
}
