package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodNameContainsKeywordsPredicate;
import seedu.sgm.model.food.FoodTypeIsWantedPredicate;

/**
 * Recommends suitable food or meals for diabetic patients.
 */
public class RecmFoodCommand extends Command {

    public static final String COMMAND_WORD = "recmf";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets a list of food recommendations."
        + "Recommendations can be filtered by keywords and flags:\n"
        + "-nsv: breakfast recommendations\n"
        + "-sv: lunch recommendations\n"
        + "-f: fruit recommendations\n"
        + "-p: protein recommendations\n"
        + "-s: snack recommendations\n"
        + "-m: meal recommendations\n"
        + "Usage:" + COMMAND_WORD + "[-FLAG]... [FOOD_NAME]";

    private final FoodTypeIsWantedPredicate typePredicate;
    private final Predicate<Food> namePredicate;

    public RecmFoodCommand(FoodTypeIsWantedPredicate typePredicate, Predicate<Food> namePredicate) {
        this.typePredicate = typePredicate;
        this.namePredicate = namePredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFoodList(food -> typePredicate.test(food) && namePredicate.test(food));
        return new CommandResult("Hope you like what I've found for you~");
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RecmFoodCommand)) {
            return false;
        }
        return typePredicate.equals(((RecmFoodCommand) other).typePredicate)
            && namePredicate.equals(namePredicate);
    }
}
