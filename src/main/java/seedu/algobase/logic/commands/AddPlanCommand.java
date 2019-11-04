package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;


/**
 * Adds a Plan to AlgoBase.
 */
public class AddPlanCommand extends Command {

    public static final String COMMAND_WORD = "addplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a Plan to AlgoBase.\n"
            + "Parameters:\n"
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE]\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2040 "
            + PREFIX_DESCRIPTION + "past year questions of CS2040 "
            + PREFIX_START_DATE + "2019-01-01 "
            + PREFIX_END_DATE + "3019-12-12";

    public static final String MESSAGE_SUCCESS = "New Plan [%1$s] added to AlgoBase.";
    public static final String MESSAGE_DUPLICATE_PLAN = "A plan with name [%1$s] already exists in AlgoBase.";

    private final Plan toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Plan}
     */
    public AddPlanCommand(Plan plan) {
        requireNonNull(plan);
        toAdd = plan;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPlan(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PLAN, toAdd.getPlanName()));
        }

        model.addPlan(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPlanCommand // instanceof handles nulls
                && toAdd.equals(((AddPlanCommand) other).toAdd));
    }
}
