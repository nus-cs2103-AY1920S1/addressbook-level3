package seedu.sugarmummy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BLOODSUGAR_CONCENTRATION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.record.Record;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Adds a record to the record list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new record to the record list. "
            + "Parameters: "
            + PREFIX_RECORDTYPE + "RECORDTYPE "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_BLOODSUGAR_CONCENTRATION + "CONCENTRATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RECORDTYPE + "BLOODSUGAR "
            + PREFIX_DATETIME + "2019-09-09 12:12 "
            + PREFIX_BLOODSUGAR_CONCENTRATION + "78.9";

    public static final String MESSAGE_SUCCESS = "New record added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the record book";

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
