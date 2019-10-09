package seedu.address.logic.commands;

import seedu.address.logic.parser.AverageCommandParser.AvgType;
import seedu.address.logic.parser.AverageCommandParser.RecordType;
import seedu.address.model.Model;

/**
 * Shows daily/weekly/monthly average of different record types.
 */
public class AverageCommand extends Command {

    public static final String COMMAND_WORD = "average";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows daily/weekly/monthly average of different "
            + "record types.\n"
            + "Parameters: a/AVERAGE_TYPE r/RECORD_TYPE [n/COUNT]\n"
            + "Example: " + COMMAND_WORD + " a/daily r/bloodsugar n/5";

    public static final String MESSAGE_INVALID_COUNT = "n/COUNT";

    private final AvgType avgType;

    private final RecordType recordType;

    private final int count;

    public AverageCommand(AvgType avgType, RecordType recordType, int count) {
        this.avgType = avgType;
        this.recordType = recordType;
        this.count = count;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format("%s, %s, %s", this.avgType, this.recordType, this.count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AverageCommand // instanceof handles nulls
                && avgType.equals(((AverageCommand) other).avgType) // state check
                && recordType.equals(((AverageCommand) other).recordType)
                && count == ((AverageCommand) other).count);
    }



}
