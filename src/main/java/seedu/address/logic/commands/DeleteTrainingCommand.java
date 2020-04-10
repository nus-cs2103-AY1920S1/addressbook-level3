package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.FLAG_TRAINING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.date.AthletickDate.DATE_FORMAT_TYPE_ONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.training.Training;
import seedu.address.ui.feature.Feature;

/**
 * Deletes a training identified by the {@code date}.
 */
public class DeleteTrainingCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + FLAG_TRAINING
            + ": Deletes the training identified by the date.\n"
            + "Parameters: " + PREFIX_DATE + "DATE\n"
            + "Date must be in the format: " + DATE_FORMAT_TYPE_ONE + "\n"
            + "Example: " + COMMAND_WORD + " " + FLAG_TRAINING + " " + PREFIX_DATE + "20102019";

    public static final String MESSAGE_DELETE_TRAINING_SUCCESS = "Deleted training on %s";

    public static final String MESSAGE_NO_TRAINING_ON_DATE = "No training found on %s";

    private final AthletickDate date;
    private Training trainingToBeDeleted;

    public DeleteTrainingCommand(AthletickDate date) {
        this.date = date;
    }
    public AthletickDate getDate() {
        return this.date;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasTrainingOn(date)) {
            Training trainingToBeDeleted = model.deleteTrainingOnDate(date);
            this.trainingToBeDeleted = trainingToBeDeleted;
        } else {
            throw new CommandException(String.format(MESSAGE_NO_TRAINING_ON_DATE, date));
        }
        date.setType(2);
        return new CommandResult(String.format(MESSAGE_DELETE_TRAINING_SUCCESS, date),
                new Feature("calendar"), date, model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTrainingCommand // instanceof handles nulls
                && date.equals(((DeleteTrainingCommand) other).date)); // state check
    }
    @Override
    public String toString() {
        return "'Delete " + trainingToBeDeleted + "' Command";
    }
}
