package seedu.sugarmummy.logic.commands.records;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BLOODSUGAR_CONCENTRATION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BMI_HEIGHT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BMI_WEIGHT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Adds a record to the record list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new record to the record list. "
            + "Parameters: "
            + PREFIX_RECORDTYPE + "RECORD_TYPE "
            + PREFIX_DATETIME + "DATETIME. "
            + "For BLOODSUGAR, additional param is: "
            + PREFIX_BLOODSUGAR_CONCENTRATION + "CONCENTRATION. "
            + "For BMI, additional param is: "
            + PREFIX_BMI_HEIGHT + "HEIGHT "
            + PREFIX_BMI_WEIGHT + "WEIGHT";

    public static final String MESSAGE_SUCCESS = "I've successfully added this new record: %1$s";
    public static final String MESSAGE_DUPLICATE_RECORD = "Oops! This record already exists in the record book.";

    private final Record toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Record}
     */
    public AddCommand(Record record) {
        requireNonNull(record);
        toAdd = record;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRecord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.addRecord(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.ADD;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
