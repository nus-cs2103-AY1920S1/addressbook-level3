package seedu.scheduler.logic.commands;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.exceptions.DuplicatePersonException;
import seedu.scheduler.model.util.CsvReader;


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
    private static final String INCORRECT_FORMAT = "Data is in incorrect format. Please refer to the "
            + "User Guide for the supported format";
    private static final String FILE_DOES_NOT_EXIST = "Target file does not exist. Please ensure that "
            + "the file path is correct.";
    private static final String DUPLICATE_PERSON_ERROR = "Data contains entries that are duplicated/already exists "
            + "in storage. Please type 'clear'(without the quote) to remove those entries before running the import "
            + "command.";
    private static final String DATE_FORMAT_ERROR_MESSAGE = "Error in data formatting: ";
    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);

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
                CsvReader csvReader = new CsvReader(filePath);
                logger.log(Level.INFO, "Starts reading .csv file");
                ArrayList<Interviewer> interviewers = csvReader.readInterviewers();
                logger.log(Level.INFO, "Finished reading .csv file");
                logger.log(Level.INFO, stringifyInterviewers(interviewers));
                for (Interviewer interviewer: interviewers) {
                    model.addInterviewer(interviewer);
                }
                return new CommandResult(SUCCESS_MESSAGE, false, false);
            } else if (this.type.equals("interviewee")) {
                CsvReader csvReader = new CsvReader(filePath);
                logger.log(Level.INFO, "Starts reading .csv file");
                ArrayList<Interviewee> interviewees = csvReader.readInterviewees();
                logger.log(Level.INFO, "Finished reading .csv file");
                logger.log(Level.INFO, stringifyInterviewees(interviewees));
                for (Interviewee interviewee: interviewees) {
                    model.addInterviewee(interviewee);
                }
                return new CommandResult(SUCCESS_MESSAGE, false, false);
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        } catch (FileNotFoundException fileE) {
            throw new CommandException(FILE_DOES_NOT_EXIST, fileE);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new CommandException(INCORRECT_FORMAT, e);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(DUPLICATE_PERSON_ERROR, dpe);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(DATE_FORMAT_ERROR_MESSAGE + iae.getMessage(), iae);
        }
    }

    /**
     * Generates a string of interviewers and their data for logging purposes.
     * @param interviewers list of {@code interviewers}.
     * @return String of all interviewers in the given list/
     */
    private static String stringifyInterviewers(ArrayList<Interviewer> interviewers) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("List of interviewers imported: \n");
        for (Interviewer interviewer: interviewers) {
            resultBuilder.append(interviewer.toString());
            resultBuilder.append("\n");
        }
        return resultBuilder.toString();
    }

    /**
     * Generates a string of interviewees and their data for logging purposes.
     * @param interviewees list of {@code interviewees}.
     * @return String of all interviewees in the given list/
     */
    private static String stringifyInterviewees(ArrayList<Interviewee> interviewees) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("List of interviewees imported: \n");
        for (Interviewee interviewee: interviewees) {
            resultBuilder.append(interviewee.toString());
            resultBuilder.append("\n");
        }
        return resultBuilder.toString();
    }
}
