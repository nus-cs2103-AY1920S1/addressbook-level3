package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.*;
import seedu.address.model.recipe.Ingredient;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }


    /**
     * Parses a {@code String ingredient} into a {@code Ingredient}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ingredient} is invalid.
     */
    public static Ingredient parseIngredient(String ingredient) throws ParseException {
        requireNonNull(ingredient);
        String trimmedIngredient = ingredient.trim();
        if (!Ingredient.isValidIngredientName(trimmedIngredient)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }
        return new Ingredient(trimmedIngredient);
    }

    /**
     * Parses {@code Collection<String> ingredients} into a {@code Set<Ingredient>}.
     */
    public static Set<Ingredient> parseIngredients(Collection<String> ingredients) throws ParseException {
        requireNonNull(ingredients);
        final Set<Ingredient> ingredientSet = new HashSet<>();
        for (String ingredientName : ingredients) {
            ingredientSet.add(parseIngredient(ingredientName));
        }
        return ingredientSet;
    }

    /**
     * Parses a {@code String calories} into a {@code Calories}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calories} is invalid.
     */
    public static Calories parseCalories(String calories) throws ParseException {
        requireNonNull(calories);
        String trimmedCalories = calories.trim();
        if (!Calories.isValidCalories(trimmedCalories)) {
            throw new ParseException(Calories.MESSAGE_CONSTRAINTS);
        }
        return new Calories(trimmedCalories);
    }

    /**
     * Parses a {@code String carbs} into a {@code Carbs}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code carbs} is invalid.
     */
    public static Carbs parseCarbs(String carbs) throws ParseException {
        requireNonNull(carbs);
        String trimmedCarbs = carbs.trim();
        if (!Carbs.isValidCarbs(trimmedCarbs)) {
            throw new ParseException(Carbs.MESSAGE_CONSTRAINTS);
        }
        return new Carbs(trimmedCarbs);
    }

    /**
     * Parses a {@code String fats} into a {@code Fats}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fats} is invalid.
     */
    public static Fats parseFats(String fats) throws ParseException {
        requireNonNull(fats);
        String trimmedFats = fats.trim();
        if (!Fats.isValidFats(trimmedFats)) {
            throw new ParseException(Fats.MESSAGE_CONSTRAINTS);
        }
        return new Fats(trimmedFats);
    }

    /**
     * Parses a {@code String protein} into a {@code Protein}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code protein} is invalid.
     */
    public static Protein parseProtein(String protein) throws ParseException {
        requireNonNull(protein);
        String trimmedProtein = protein.trim();
        if (!Protein.isValidProtein(trimmedProtein)) {
            throw new ParseException(Protein.MESSAGE_CONSTRAINTS);
        }
        return new Protein(trimmedProtein);
    }
}
