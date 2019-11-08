package seedu.sugarmummy.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.statistics.predicates.RecordContainsRecordTypePredicate;
import seedu.sugarmummy.ui.DisplayPaneType;

//@@author chen-xi-cx

/**
 * Shows daily/weekly/monthly average of different record types in a line graph.
 */
public class AverageCommand extends Command {

    public static final String COMMAND_WORD = "average";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows daily/weekly/monthly average of different "
            + "record types in a line graph.\n"
            + "Format: average a/AVERAGE_TYPE rt/RECORD_TYPE [n/COUNT]\n"
            + "Example: " + COMMAND_WORD + " a/daily rt/bloodsugar n/5";

    public static final String MESSAGE_SUCCESS = "Your %1$s averages for %2$s have been calculated successfully!";

    public static final String MESSAGE_INVALID_COUNT = "n/COUNT";

    public static final String MESSAGE_INVALID_AVGTYPE = "a/AVERAGE_TYPE";

    public static final String MESSAGE_INVALID_RECORD_TYPE = "rt/RECORD_TYPE";

    public static final String MESSAGE_NO_RECORD = "Oops! You do not have any %1$s record.";

    private final AverageType averageType;

    private final RecordType recordType;

    private final RecordContainsRecordTypePredicate recordContainsRecordTypePredicate;

    private final int count;

    public AverageCommand(RecordContainsRecordTypePredicate recordContainsRecordTypePredicate,
            AverageType averageType, RecordType recordType, int count) {
        requireNonNull(averageType);
        requireNonNull(recordType);
        this.averageType = averageType;
        this.recordType = recordType;
        this.count = count;
        this.recordContainsRecordTypePredicate = recordContainsRecordTypePredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredRecordList(recordContainsRecordTypePredicate);
        model.calculateAverageMap(averageType, recordType, count);

        if (model.getAverageMap().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_RECORD, recordType));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, averageType, recordType));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.AVERAGE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AverageCommand // instanceof handles nulls
                && averageType.equals(((AverageCommand) other).averageType) // state check
                && recordType.equals(((AverageCommand) other).recordType)
                && count == ((AverageCommand) other).count)
                && recordContainsRecordTypePredicate.equals(((AverageCommand) other).recordContainsRecordTypePredicate);
    }
}
