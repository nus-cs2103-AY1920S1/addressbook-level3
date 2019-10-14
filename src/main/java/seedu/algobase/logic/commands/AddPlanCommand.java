package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;


/**
 * Adds a Plan to AlgoBase.
 */
public class AddPlanCommand extends Command {

    public static final String COMMAND_WORD = "addplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Plan to the algobase. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_START_DATE + "START_DATE "
            + PREFIX_END_DATE + "END_DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2040 "
            + PREFIX_DESCRIPTION + "past year questions of CS2040 "
            + PREFIX_START_DATE + "2019/01/01"
            + PREFIX_END_DATE + "3019/12/12";

    public static final String MESSAGE_SUCCESS = "New Plan added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAN = "This Plan already exists in the algobase";

    private final Plan toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Plan}
     */
    public AddPlanCommand(Plan plan) {
        requireNonNull(plan);
        toAdd = plan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPlan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAN);
        }

        model.addPlan(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPlanCommand // instanceof handles nulls
                && toAdd.equals(((AddPlanCommand) other).toAdd));
    }
}
