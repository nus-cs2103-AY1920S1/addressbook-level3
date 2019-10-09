package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.sgm.model.food.FoodNameContainsKeywordsPredicate;

/**
 * Recommends suitable food or meals for diabetic patients.
 */
public class RecmFoodCommand extends Command {

    public static final String COMMAND_WORD = "recmf";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets a list of food recommendations."
            + "Recommendations can be filtered by flags or food names:\n"
            + "-b: breakfast recommendations\n"
            + "-l: lunch recommendations\n"
            + "-d: dinner recommendations\n"
            + "-f: fruit recommendations\n"
            + "-v: vegetable recommendations";

    private final FoodNameContainsKeywordsPredicate predicate;

    public RecmFoodCommand(FoodNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //model.updateFilteredFoodMap(predicate);
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
        return predicate.equals(((RecmFoodCommand) other).predicate);
    }
}
