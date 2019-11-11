package seedu.sugarmummy.logic.commands.recmf;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodComparator;
import seedu.sugarmummy.model.recmf.FoodType;
import seedu.sugarmummy.model.recmf.predicates.FoodTypeIsWantedPredicate;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Recommends suitable food or meals for diabetic patients.
 */
public class RecmFoodCommand extends Command {

    public static final String COMMAND_WORD = "recmf";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets food recommendations, which can be filtered "
            + "(by flags or food names) and sorted.\n"
            + "Flags are based on the following food types: "
            + FoodType.getAllTypesInfo() + "\n"
            + "Usage: " + COMMAND_WORD + " [-FLAG]... [fn/FOOD_NAME...] [±sort/SORT_ORDER_TYPE]\n"
            + "Example: recmf -p -nsv +sort/gi";

    public static final String MESSAGE_RESPONSE_EMPTY_FOOD_LIST = "Oops! There is no food to recommend :(\n"
            + "It may be due to empty food database or no matching foods.";
    private static final String MESSAGE_RESPONSE_NORMAL_LIST = "Hope you like what I've found for you~";

    private final FoodTypeIsWantedPredicate typePredicate;
    private final Predicate<Food> namePredicate;
    private final FoodComparator foodComparator;

    public RecmFoodCommand(FoodTypeIsWantedPredicate typePredicate, Predicate<Food> foodNamePredicate,
                           FoodComparator foodComparator) {
        this.typePredicate = typePredicate;
        this.namePredicate = foodNamePredicate;
        this.foodComparator = foodComparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFoodList(typePredicate.and(namePredicate));
        model.sortFoodList(foodComparator);
        if (model.getFilterFoodList().size() == 0) {
            return new CommandResult(MESSAGE_RESPONSE_EMPTY_FOOD_LIST);
        }
        return new CommandResult(MESSAGE_RESPONSE_NORMAL_LIST);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.RECM_FOOD;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RecmFoodCommand)) {
            return false;
        }
        RecmFoodCommand another = (RecmFoodCommand) other;
        return typePredicate.equals(another.typePredicate)
                && namePredicate.equals(another.namePredicate)
                && foodComparator.equals(another.foodComparator);
    }
}
