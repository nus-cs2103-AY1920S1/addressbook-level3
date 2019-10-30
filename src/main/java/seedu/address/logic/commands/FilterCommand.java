package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.Model;
import seedu.address.model.entity.body.BodyContainsAttributesKeywordsPredicate;
import seedu.address.model.entity.worker.WorkerContainsAttributesKeywordsPredicate;

//@@author dalisc
/**
 * Lists all persons in the address book to the user.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters entities by attributes supplied\n"
            + "Parameters: FLAG (must be either b/w) /ATTRIBUTE attribute\n"
            + "Example: " + COMMAND_WORD + " -b /religion buddhism /cod natural";

    private final ArgumentMultimap argumentMultimap;
    private final BodyContainsAttributesKeywordsPredicate bodyPredicate;
    private final WorkerContainsAttributesKeywordsPredicate workerPredicate;
    private final String flag;

    public FilterCommand(ArgumentMultimap argumentMultimap, String flag) {
        this.argumentMultimap = argumentMultimap;
        this.bodyPredicate = new BodyContainsAttributesKeywordsPredicate(argumentMultimap);
        this.workerPredicate = new WorkerContainsAttributesKeywordsPredicate(argumentMultimap);
        this.flag = flag;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (flag.equals("b")) {
                model.updateFilteredBodyList(bodyPredicate);
                return new CommandResult(
                        String.format(Messages.MESSAGE_BODIES_LISTED_OVERVIEW, model.getFilteredBodyList().size()));
            } else if (flag.equals("w")) {
                model.updateFilteredWorkerList(workerPredicate);
                return new CommandResult(
                        String.format(Messages.MESSAGE_WORKERS_LISTED_OVERVIEW, model.getFilteredWorkerList().size()));
            } else {
                throw new CommandException(Messages.MESSAGE_NO_FLAG);
            }
        } catch (CommandException e) {
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && bodyPredicate.equals(((FilterCommand) other).bodyPredicate)
                && workerPredicate.equals(((FilterCommand) other).workerPredicate)); // state check
    }
}

