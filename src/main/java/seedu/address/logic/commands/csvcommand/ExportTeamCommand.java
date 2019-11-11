package seedu.address.logic.commands.csvcommand;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports every {@code Team} stored inside {@code Alfred} into an external CSV file.
 */
public class ExportTeamCommand extends ExportCommand {

    public static final String MESSAGE_SUCCESS = "Exported all teams to %s"; // %s -> file name

    public ExportTeamCommand(String csvFilePath) {
        super(csvFilePath);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.getTeamList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_DATA);
        }
        try {
            File csvFile = this.csvFilePath.toFile();
            FileUtil.createIfMissing(this.csvFilePath);
            CsvUtil.writeToCsv(csvFile, model.getTeamList());
            model.recordCommandExecution(this.getCommandInputString());
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_IO_EXCEPTION);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.csvFilePath.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ExportTeamCommand
                && super.equals(other);
    }

}
