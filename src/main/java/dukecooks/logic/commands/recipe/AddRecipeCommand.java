package dukecooks.logic.commands.recipe;

import static dukecooks.logic.parser.CliSyntax.PREFIX_CALORIES;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CARBS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_FATS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.recipe.components.Recipe;

/**
 * Adds a recipe to Duke Cooks.
 */
public class AddRecipeCommand extends AddCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe to Duke Cooks. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_INGREDIENT + "INGREDIENTS... "
            + PREFIX_CALORIES + "CALORIES "
            + PREFIX_CARBS + "CARBS "
            + PREFIX_FATS + "FATS "
            + PREFIX_PROTEIN + "PROTEIN\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Cheese Omelette "
            + PREFIX_INGREDIENT + "eggs "
            + PREFIX_INGREDIENT + "cheese "
            + PREFIX_CALORIES + "358 "
            + PREFIX_CARBS + "1 "
            + PREFIX_FATS + "28 "
            + PREFIX_PROTEIN + "21 ";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in Duke Cooks";

    private static Event event;

    private final Recipe toAdd;

    /**
     * Creates an AddRecipeCommand to add the specified {@code Recipe}
     */
    public AddRecipeCommand(Recipe recipe) {
        requireNonNull(recipe);
        toAdd = recipe;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRecipe(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.addRecipe(toAdd);

        event = Event.getInstance();
        event.set("recipe", "all");

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecipeCommand // instanceof handles nulls
                && toAdd.equals(((AddRecipeCommand) other).toAdd));
    }
}
