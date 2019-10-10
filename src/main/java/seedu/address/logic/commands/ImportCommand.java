package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.model.Model;
import seedu.address.model.util.ExcelReader;


/**
 * Import excel file containing interviewee's information.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import excel file containing "
            + "interviewee's information.\n"
            + "Example: " + COMMAND_WORD + "<excelFilePath>";

    public static final String SHOWING_MESSAGE = "Data imported successfully.";

    private String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) {
        String result = SHOWING_MESSAGE;
        try {
            ExcelReader excelReader = new ExcelReader(filePath);
            result = excelReader.translate();

        } catch (IOException e) {
            e.printStackTrace();
            result = "File is in wrong format";
        }

        return new CommandResult(result, false, false);

    }
}
