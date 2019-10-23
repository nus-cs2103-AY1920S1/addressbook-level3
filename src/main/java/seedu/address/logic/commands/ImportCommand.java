package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Schedule;
import seedu.address.model.util.CsvReader;





/**
 * Import csv file containing interviewer's/ interviewers's information.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import .csv file containing "
            + "interviewer or interviewee's information.\n"
            + "Example: " + COMMAND_WORD + " interviewer " + "<csvFilePath>";

    public static final String SUCCESS_MESSAGE = "Data imported successfully.";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Command not implemented yet";
    public static final String INCORRECT_FORMAT = "Data is in incorrect format. Please refer to the "
            + "User Guide for the supported format";
    public static final String FILE_DOES_NOT_EXIST = "Target file does not exist. Please ensure that "
            + "the file path is correct.";

    private String filePath;
    private String type;

    public ImportCommand(String args) {
        String[] strings = args.split(" ");
        this.type = strings[0];
        this.filePath = strings[1];
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            if (this.type.equals("interviewer")) {
                ArrayList<Schedule> schedules;
                CsvReader csvReader = new CsvReader(filePath);
                schedules = csvReader.read();
                model.setSchedulesList(schedules);
                return new CommandResult(SUCCESS_MESSAGE, false, false);
            } else if (this.type.equals("interviewee")) {
                return new CommandResult(MESSAGE_NOT_IMPLEMENTED_YET, false, false);
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        } catch (FileNotFoundException fileE) {
            throw new CommandException(FILE_DOES_NOT_EXIST, fileE);
        } catch (IOException ioe) {
            throw new CommandException("Failed", ioe);
        } catch (ArrayIndexOutOfBoundsException arrayE) {
            throw new CommandException(INCORRECT_FORMAT, arrayE);
        }
    }
}
