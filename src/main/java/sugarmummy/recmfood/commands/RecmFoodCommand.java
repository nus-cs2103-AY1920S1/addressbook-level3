package sugarmummy.recmfood.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.DisplayPaneType;
import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.predicates.FoodTypeIsWantedPredicate;

/**
 * Recommends suitable food or meals for diabetic patients.
 */
public class RecmFoodCommand extends Command {

    private static final String MESSAGE_RESPONSE_EMPTY_FOOD_LIST = "There is no match in the current database :( "
            + "Try adding more new foods or reducing some filters~";
    private static final String MESSAGE_RESPONSE_NORMAL_LIST = "Hope you like what I've found for you~";

    public static final char FLAG_SIGNAL = '-';
    public static final String COMMAND_WORD = "recmf";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets a list of food recommendations."
            + "Recommendations can be filtered by keywords and flags:\n"
            + "-nsv: breakfast recommendations\n"
            + "-sv: lunch recommendations\n"
            + "-f: fruit recommendations\n"
            + "-p: protein recommendations\n"
            + "-s: snack recommendations\n"
            + "-m: meal recommendations\n"
            + "Usage:" + COMMAND_WORD + "[-FLAG]... [fn/FOOD_NAME]";

    private final FoodTypeIsWantedPredicate typePredicate;
    private final Predicate<Food> namePredicate;

    public RecmFoodCommand(FoodTypeIsWantedPredicate typePredicate, Predicate<Food> foodNamePredicate) {
        this.typePredicate = typePredicate;
        this.namePredicate = foodNamePredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFoodList(food -> typePredicate.test(food) && namePredicate.test(food));
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
        return typePredicate.equals(((RecmFoodCommand) other).typePredicate)
                && namePredicate.equals(namePredicate);
    }
}
